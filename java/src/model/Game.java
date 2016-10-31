package model;

import client.communication.LogEntry;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.map.MapController;
import client.states.*;
import com.google.gson.JsonParser;
import decoder.JsonModels.*;
import model.cards_resources.Bank;
import model.cards_resources.DevelopmentCard;
import model.cards_resources.ResourceCards;
import model.cards_resources.Trade;
import model.map.Hex;
import model.map.Map;
import model.map.Port;
import model.pieces.Building;
import model.pieces.City;
import model.pieces.Road;
import model.pieces.Settlement;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.*;
import sun.security.x509.EDIPartyName;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Game class, contains collections of all model objects within the game
 * Singleton because there can Only Be One
 * Extends Observable to notify Controllers(Observers) when there has been a change to the model
 *
 *
 */
public class Game extends Observable {

    private static Game instance = null;

    private int versionNumber = 1;
    private Bank bank;
    private List<LogEntry> chatLog = new ArrayList<LogEntry>();
    private List<LogEntry> historyLog = new ArrayList<LogEntry>();
    private Map map;
    private List<Player> playersList;
    private Player player;
    private Trade activeTrade; //null if none is ongoing
    private TurnTracker turnTracker;
    private Player winner;


    private GameInfo gameInfo;
    private PlayerInfo currentPlayerInfo;
    private GameInfo[] allGameInfos;


    public void replaceModel(JsonModel model)
    {
        //Version Number
        this.versionNumber = model.getVersion();

        //NEW BANK
        ArrayList<DevelopmentCard> deck = createDeck(model);
        ResourceCards pool = new ResourceCards(model.getBank().getBrick(),
                                                model.getBank().getOre(),
                                                model.getBank().getSheep(),
                                                model.getBank().getWheat(),
                                                model.getBank().getWood());
        this.bank = new Bank(pool, deck);

        ArrayList<LogEntry> chat = createLogList(model.getChat().getLines(), model.getPlayers(), "chat");
        ArrayList<LogEntry> log = createLogList(model.getLog().getLines(), model.getPlayers(), "history");
        addHacks(log, model.getChat().getLines(), model.getPlayers());

        this.chatLog = chat;
        this.historyLog = log;


        //replace map
        ArrayList<Hex> hexes = createHexList(model.getMap().getHexes());
        ArrayList<Building> buildings = createBuildingList(model.getMap().getCities(), model.getMap().getSettlements(), model.getPlayers());
        ArrayList<Road> roads = createRoadList(model.getMap().getRoads(), model.getPlayers());
        ArrayList<Port> ports = createPortList(model.getMap().getPorts());
        HexLocation robber = new HexLocation(model.getMap().getRobber().getX(),model.getMap().getRobber().getY());

        this.map = new Map(hexes, buildings,roads, ports, model.getMap().getRadius(), robber);

        //replace List<Players> players
        playersList = createPlayersList(model.getPlayers());

        //replace Player
        for(int i = 0; i < playersList.size(); i++)
        {
           if(playersList.get(i).getPlayerInfo().getId() == this.player.getPlayerInfo().getId())
            {
                this.player = playersList.get(i);
            }
        }

        //update GameInfo
        ArrayList<PlayerInfo> gameInfoPlayers = new ArrayList<>();
        for(int i = 0; i < playersList.size(); i++)
        {
            if(playersList.get(i) != null)
            {
                gameInfoPlayers.add(playersList.get(i).getPlayerInfo());
            }
        }
        this.gameInfo.setPlayers(gameInfoPlayers);



        //replace activeTrade
        this.activeTrade = createActiveTrade(model.getTradeOffer());

        //replace turnTracker
        TurnTracker tracker = new TurnTracker(model.getTurnTracker().getStatus(),
                model.getTurnTracker().getCurrentTurn(),
                model.getTurnTracker().getLongestRoad(),
                model.getTurnTracker().getLargestArmy());
        this.turnTracker = tracker;

        //updates the state in the mapcontroller
        setState(model.getTurnTracker().getStatus());

        //replace winner
        this.versionNumber = model.getVersion();

        // Marks this Observable object as having been changed; the hasChanged method will now return true.
        this.setChanged();
        // If this object has changed, as indicated by the hasChanged method, then notify all of
        // its observers and then call the clearChanged method to indicate that this object has no longer changed.
        this.notifyObservers();

    }

    private void setState(String status) {

        IGameState newState = null;

        switch(status)
        {
            case "Rolling":
                newState= new RollingState();
                break;

            case "FirstRound":
                newState= new FirstRoundState();
                break;

            case "SecondRound":
                newState= new SecondRoundState();
                break;

            case "Playing":
                newState= new PlayingState();
                break;

            case "Robbing":
                newState= new RollingState();
                break;
            

        }

        MapController.setState(newState);

    }

    private void addHacks(ArrayList<LogEntry> log, JsonLine[] lines, JsonPlayer[] players) {

        for(int i = 0; i < lines.length; i++)
        {
            if(historyHack(lines[i].getMessage()))
            {
                CatanColor color = null;
                for (int j = 0; j < players.length; j++) {
                    if (players[j] != null && (lines[i].getSource().equals(players[j].getName()))) {
                        switch (players[j].getColor()) {

                            case "red":
                                color = CatanColor.RED;
                                break;
                            case "orange":
                                color = CatanColor.ORANGE;
                                break;
                            case "yellow":
                                color = CatanColor.YELLOW;
                                break;
                            case "blue":
                                color = CatanColor.BLUE;
                                break;
                            case "green":
                                color = CatanColor.GREEN;
                                break;
                            case "purple":
                                color = CatanColor.PURPLE;
                                break;
                            case "puce":
                                color = CatanColor.PUCE;
                                break;
                            case "white":
                                color = CatanColor.WHITE;
                                break;
                            case "brown":
                                color = CatanColor.BROWN;
                                break;
                            default:
                                color = CatanColor.BLUE;

                        }
                    }
                }

                String message = lines[i].getMessage().split("#showmethemoney ", 2)[1];

                LogEntry newLog = new LogEntry(color, message);
                log.add(newLog);
            }
        }

    }

    public Trade createActiveTrade(JsonTradeOffer tradeOffer)
    {
        if(tradeOffer == null)
        {
            return null;
        }
        else
        {

            Trade newTrade = null;
            int sender = tradeOffer.getSender();
            int receiver = tradeOffer.getReceiver();

            ResourceCards offer;
            int offerBrick;
            int offerOre;
            int offerSheep;
            int offerWheat;
            int offerWood;

            ResourceCards request;
            int requestBrick;
            int requestOre;
            int requestSheep;
            int requestWheat;
            int requestWood;

            if(tradeOffer.getOffer().getBrick() >= 0)
            {
                offerBrick = tradeOffer.getOffer().getBrick();
                requestBrick = 0;
            }
            else
            {
                offerBrick = 0;
                requestBrick = Math.abs(tradeOffer.getOffer().getBrick());

            }

            if(tradeOffer.getOffer().getOre() >= 0)
            {
                offerOre = tradeOffer.getOffer().getOre();
                requestOre = 0;
            }
            else
            {
                offerOre = 0;
                requestOre = Math.abs(tradeOffer.getOffer().getOre());
            }

            if(tradeOffer.getOffer().getSheep() >= 0)
            {
                offerSheep = tradeOffer.getOffer().getSheep();
                requestSheep = 0;
            }
            else
            {
                offerSheep = 0;
                requestSheep = Math.abs(tradeOffer.getOffer().getSheep());

            }

            if(tradeOffer.getOffer().getWheat() >= 0)
            {
                offerWheat = tradeOffer.getOffer().getWheat();
                requestWheat = 0;
            }
            else
            {
                offerWheat = 0;
                requestWheat = Math.abs(tradeOffer.getOffer().getWheat());

            }

            if(tradeOffer.getOffer().getWood() >= 0)
            {
                offerWood = tradeOffer.getOffer().getWood();
                requestWood = 0;
            }
            else
            {
                offerWood = 0;
                requestWood = Math.abs(tradeOffer.getOffer().getWood());

            }

            offer = new ResourceCards(offerBrick, offerOre, offerSheep, offerWheat ,offerWood);
            request = new ResourceCards(requestBrick, requestOre, requestSheep ,requestWheat, requestWood);

            newTrade = new Trade(sender, receiver, offer, request);

            return newTrade;
        }
    }

    public ArrayList<Player> createPlayersList(JsonPlayer[] players)
    {
        try {
            ArrayList<Player> playersList = new ArrayList<>();

            for(int i = 0; i < players.length; i++)
            {
                if(players[i] != null) {

                    CatanColor color = null;
                    switch (players[i].getColor()) {
                        //RED, ORANGE, YELLOW, BLUE, GREEN, PURPLE, PUCE, WHITE, BROWN;

                        case "red":
                            color = CatanColor.RED;
                            break;
                        case "orange":
                            color = CatanColor.ORANGE;
                            break;
                        case "yellow":
                            color = CatanColor.YELLOW;
                            break;
                        case "blue":
                            color = CatanColor.BLUE;
                            break;
                        case "green":
                            color = CatanColor.GREEN;
                            break;
                        case "purple":
                            color = CatanColor.PURPLE;
                            break;
                        case "puce":
                            color = CatanColor.PUCE;
                            break;
                        case "white":
                            color = CatanColor.WHITE;
                            break;
                        case "brown":
                            color = CatanColor.BROWN;
                            break;
                        default:
                            color = CatanColor.BLUE;

                    }

                    JsonResource r = players[i].getResources();
                    ResourceCards resourceCards = new ResourceCards(r.getBrick(), r.getOre(),
                            r.getSheep(), r.getWheat(), r.getWood());

                    ArrayList<DevelopmentCard> newDevCards = createDevCardList(players[i].getNewDevCards());
                    ArrayList<DevelopmentCard> oldDevCards = createDevCardList(players[i].getOldDevCards());


                    PlayerID id = new PlayerID(players[i].getPlayerID());

                    Player newPlayer = new Player(players[i].getName(),
                            color,
                            players[i].getSettlements(),
                            players[i].getCities(),
                            players[i].getRoads(),
                            resourceCards,
                            oldDevCards,
                            newDevCards,
                            id,
                            players[i].getPlayerIndex(),
                            players[i].isDiscarded(),
                            players[i].isPlayedDevCard(),
                            players[i].getMonuments(),
                            players[i].getSoldiers(),
                            players[i].getVictoryPoints());

                    playersList.add(newPlayer);
                }
            }

            return playersList;

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<DevelopmentCard> createDevCardList(JsonDevCard devCards)
    {
        ArrayList<DevelopmentCard> newDevCardList = new ArrayList<>();

        for(int i = 0; i < devCards.getSoldier(); i++)
        {
            DevelopmentCard newCard = new DevelopmentCard(DevCardType.SOLDIER);
            newDevCardList.add(newCard);
        }

        for(int i = 0; i < devCards.getYearOfPlenty(); i++)
        {
            DevelopmentCard newCard = new DevelopmentCard(DevCardType.YEAR_OF_PLENTY);
            newDevCardList.add(newCard);
        }

        for(int i = 0; i < devCards.getRoadBuilding(); i++)
        {
            DevelopmentCard newCard = new DevelopmentCard(DevCardType.ROAD_BUILD);
            newDevCardList.add(newCard);
        }

        for(int i = 0; i < devCards.getMonopoly(); i++)
        {
            DevelopmentCard newCard = new DevelopmentCard(DevCardType.MONOPOLY);
            newDevCardList.add(newCard);
        }

        for(int i = 0; i < devCards.getMonument(); i++)
        {
            DevelopmentCard newCard = new DevelopmentCard(DevCardType.MONUMENT);
            newDevCardList.add(newCard);
        }

        return newDevCardList;
    }

    public ArrayList<Road> createRoadList(JsonPiece[] roads, JsonPlayer[] players)
    {
        ArrayList<Road> roadList = new ArrayList<>();

        if(roads != null) {
            for (int i = 0; i < roads.length; i++) {
                CatanColor color = null;

                for(int j = 0; j < players.length; j++) {
                    if (players[j].getPlayerIndex() == roads[i].getOwner()) {
                        switch (players[j].getColor()) {
                            //RED, ORANGE, YELLOW, BLUE, GREEN, PURPLE, PUCE, WHITE, BROWN;

                            case "red":
                                color = CatanColor.RED;
                                break;
                            case "orange":
                                color = CatanColor.ORANGE;
                                break;
                            case "yellow":
                                color = CatanColor.YELLOW;
                                break;
                            case "blue":
                                color = CatanColor.BLUE;
                                break;
                            case "green":
                                color = CatanColor.GREEN;
                                break;
                            case "purple":
                                color = CatanColor.PURPLE;
                                break;
                            case "puce":
                                color = CatanColor.PUCE;
                                break;
                            case "white":
                                color = CatanColor.WHITE;
                                break;
                            case "brown":
                                color = CatanColor.BROWN;
                                break;
                            default:
                                color = CatanColor.BLUE;

                        }
                    }
                }

                HexLocation hexLocation = new HexLocation(roads[i].getLocation().getX(), roads[i].getLocation().getY());

                EdgeDirection dir;

                //NorthWest, North, NorthEast, SouthEast, South, SouthWest;

                switch (roads[i].getLocation().getDirection()) {
                    case "N":
                        dir = EdgeDirection.North;
                        break;
                    case "NW":
                        dir = EdgeDirection.NorthWest;
                        break;
                    case "NE":
                        dir = EdgeDirection.NorthEast;
                        break;
                    case "S":
                        dir = EdgeDirection.South;
                        break;
                    case "SE":
                        dir = EdgeDirection.SouthEast;
                        break;
                    case "SW":
                        dir = EdgeDirection.SouthWest;
                        break;
                    default:
                        dir = EdgeDirection.NorthEast;

                }

                EdgeLocation loc = new EdgeLocation(hexLocation, dir);


                Road newRoad = new Road(color, loc);

                roadList.add(newRoad);
            }
        }

        return roadList;
    }

    public ArrayList<Port> createPortList(JsonPiece[] ports)
    {
        ArrayList<Port> portList = new ArrayList<>();

        if(ports != null) {

            for (int i = 0; i < ports.length; i++) {
                HexLocation hexLocation = new HexLocation(ports[i].getLocation().getX(), ports[i].getLocation().getY());

                ResourceType resource;

                if(ports[i].getResource() == null)
                {
                    resource = null;
                }
                else {


                    switch (ports[i].getResource()) {
                        case "wood":
                            resource = ResourceType.WOOD;
                            break;
                        case "ore":
                            resource = ResourceType.ORE;
                            break;
                        case "sheep":
                            resource = ResourceType.SHEEP;
                            break;
                        case "wheat":
                            resource = ResourceType.WHEAT;
                            break;
                        case "brick":
                            resource = ResourceType.BRICK;
                            break;
                        default:
                            resource = null;
                            break;
                    }
                }


                //NorthWest, North, NorthEast, SouthEast, South, SouthWest;

                EdgeDirection direction = null;
                switch (ports[i].getDirection())
                {
                    case "NW":
                        direction = EdgeDirection.NorthWest;
                        break;
                    case "N":
                        direction = EdgeDirection.North;
                        break;
                    case "NE":
                        direction = EdgeDirection.NorthEast;
                        break;
                    case "SE":
                        direction = EdgeDirection.SouthEast;
                        break;
                    case "S":
                        direction = EdgeDirection.South;
                        break;
                    case "SW":
                        direction = EdgeDirection.SouthWest;
                        break;
                }


                Port newPort = new Port(resource, hexLocation, direction, ports[i].getRatio());

                portList.add(newPort);
            }
        }

        return portList;

    }

    public ArrayList<Hex> createHexList(JsonHex[] hexes)
    {
        ArrayList<Hex> hexList = new ArrayList<>();

        for(int i = 0; i < hexes.length; i++)
        {

            HexType type;

            if(hexes[i].getResource() == null)
            {
                type = HexType.DESERT;
            }
            else {
                switch (hexes[i].getResource()) {
                    case "wood":
                        type = HexType.WOOD;
                        break;
                    case "sheep":
                        type = HexType.SHEEP;
                        break;
                    case "wheat":
                        type = HexType.WHEAT;
                        break;
                    case "brick":
                        type = HexType.BRICK;
                        break;
                    case "ore":
                        type = HexType.ORE;
                        break;
                    case "desert":
                        type = HexType.DESERT;
                        break;
                    case "water":
                        type = HexType.WATER;
                        break;
                    default:
                        type = HexType.WATER;
                        break;
                }
            }

            //location
            HexLocation hexLocation = new HexLocation(hexes[i].getLocation().getX(), hexes[i].getLocation().getY());

            //number
            int hexNumber = hexes[i].getNumber();

            Hex newHex = new Hex(type, hexLocation, hexNumber);
            hexList.add(newHex);
        }

        return hexList;

    }

    public ArrayList<Building> createBuildingList(JsonPiece[] cities, JsonPiece[] settlements, JsonPlayer[] players)
    {
        ArrayList<Building> buildings = new ArrayList<>();

        if(cities != null) {

            for (int i = 0; i < cities.length; i++) {

                HexLocation hexLocation = new HexLocation(cities[i].getLocation().getX(), cities[i].getLocation().getY());

                VertexDirection dir;

                switch (cities[i].getLocation().getDirection()) {
                    case "W":
                        dir = VertexDirection.West;
                        break;
                    case "NW":
                        dir = VertexDirection.NorthWest;
                        break;
                    case "NE":
                        dir = VertexDirection.NorthEast;
                        break;
                    case "E":
                        dir = VertexDirection.East;
                        break;
                    case "SE":
                        dir = VertexDirection.SouthEast;
                        break;
                    case "SW":
                        dir = VertexDirection.SouthWest;
                        break;
                    default:
                        dir = VertexDirection.NorthEast;

                }

                CatanColor color = null;

                for (int j = 0; j < players.length; j++) {
                    if (players[j].getPlayerIndex() == cities[i].getOwner()) {
                        switch (players[i].getColor()) {

                            case "red":
                                color = CatanColor.RED;
                                break;
                            case "orange":
                                color = CatanColor.ORANGE;
                                break;
                            case "yellow":
                                color = CatanColor.YELLOW;
                                break;
                            case "blue":
                                color = CatanColor.BLUE;
                                break;
                            case "green":
                                color = CatanColor.GREEN;
                                break;
                            case "purple":
                                color = CatanColor.PURPLE;
                                break;
                            case "puce":
                                color = CatanColor.PUCE;
                                break;
                            case "white":
                                color = CatanColor.WHITE;
                                break;
                            case "brown":
                                color = CatanColor.BROWN;
                                break;
                            default:
                                color = CatanColor.BLUE;

                        }
                    }

                }


                VertexLocation loc = new VertexLocation(hexLocation, dir);

                Building newBuilding = new City(color, loc);
                buildings.add(newBuilding);
            }
        }

        if(settlements != null) {


            for (int i = 0; i < settlements.length; i++) {
                HexLocation hexLocation = new HexLocation(settlements[i].getLocation().getX(), settlements[i].getLocation().getY());

                VertexDirection dir;

                switch (settlements[i].getLocation().getDirection()) {
                    case "W":
                        dir = VertexDirection.West;
                        break;
                    case "NW":
                        dir = VertexDirection.NorthWest;
                        break;
                    case "NE":
                        dir = VertexDirection.NorthEast;
                        break;
                    case "E":
                        dir = VertexDirection.East;
                        break;
                    case "SE":
                        dir = VertexDirection.SouthEast;
                        break;
                    case "SW":
                        dir = VertexDirection.SouthWest;
                        break;
                    default:
                        dir = VertexDirection.NorthEast;

                }

                CatanColor color = null;



                for (int j = 0; j < players.length; j++) {
                    if (players[j].getPlayerIndex() == settlements[i].getOwner()) {
                        switch (players[j].getColor()) {

                            case "red":
                                color = CatanColor.RED;
                                break;
                            case "orange":
                                color = CatanColor.ORANGE;
                                break;
                            case "yellow":
                                color = CatanColor.YELLOW;
                                break;
                            case "blue":
                                color = CatanColor.BLUE;
                                break;
                            case "green":
                                color = CatanColor.GREEN;
                                break;
                            case "purple":
                                color = CatanColor.PURPLE;
                                break;
                            case "puce":
                                color = CatanColor.PUCE;
                                break;
                            case "white":
                                color = CatanColor.WHITE;
                                break;
                            case "brown":
                                color = CatanColor.BROWN;
                                break;
                            default:
                                color = CatanColor.BLUE;

                        }
                    }

                }


                VertexLocation loc = new VertexLocation(hexLocation, dir);

                Building newBuilding = new Settlement(color, loc);
                buildings.add(newBuilding);
            }
        }

        return buildings;

    }

    public ArrayList<LogEntry> createLogList(JsonLine[] lines, JsonPlayer[] players, String type)
    {

        ArrayList<LogEntry> messageList = new ArrayList<>();

        for(int i = 0; i < lines.length; i++)
        {
            if((type.equals("chat") && !historyHack(lines[i].getMessage())) || type.equals("history")) {

                CatanColor color = null;
                for (int j = 0; j < players.length; j++) {
                    if (players[j] != null && (lines[i].getSource().equals(players[j].getName()))) {
                        switch (players[j].getColor()) {

                            case "red":
                                color = CatanColor.RED;
                                break;
                            case "orange":
                                color = CatanColor.ORANGE;
                                break;
                            case "yellow":
                                color = CatanColor.YELLOW;
                                break;
                            case "blue":
                                color = CatanColor.BLUE;
                                break;
                            case "green":
                                color = CatanColor.GREEN;
                                break;
                            case "purple":
                                color = CatanColor.PURPLE;
                                break;
                            case "puce":
                                color = CatanColor.PUCE;
                                break;
                            case "white":
                                color = CatanColor.WHITE;
                                break;
                            case "brown":
                                color = CatanColor.BROWN;
                                break;
                            default:
                                color = CatanColor.BLUE;

                        }
                    }
                }
                messageList.add(new LogEntry(color, lines[i].getMessage()));
            }
        }

        return messageList;

    }

    public boolean historyHack(String message)
    {
        return message.contains("#showmethemoney");
    }
    
    public void addChatMessage(LogEntry entry) {
    	chatLog.add(entry);
    }
    
    public void addHistoryEntry(LogEntry entry) {
    	historyLog.add(entry);
    }
    
    public List<LogEntry> getChatLog() {
    	return chatLog;
    }
    
    public List<LogEntry> getHistory() {
    	return historyLog;
    }

    public ArrayList<DevelopmentCard> createDeck(JsonModel model)
    {
        JsonDeck newDeck = model.getDeck();

        ArrayList<DevelopmentCard> deck = new ArrayList<>();

        for(int i = 0; i < newDeck.getMonopoly(); i++)
        {
            deck.add(new DevelopmentCard(DevCardType.MONOPOLY));
        }

        for(int i = 0; i < newDeck.getMonument(); i++)
        {
            deck.add(new DevelopmentCard(DevCardType.MONUMENT));
        }

        for(int i = 0; i < newDeck.getRoadBuilding(); i++)
        {
            deck.add(new DevelopmentCard(DevCardType.ROAD_BUILD));
        }

        for(int i = 0; i < newDeck.getSoldier(); i++)
        {
            deck.add(new DevelopmentCard(DevCardType.SOLDIER));
        }

        for (int i = 0; i < newDeck.getYearOfPlenty(); i++)
        {
            deck.add(new DevelopmentCard(DevCardType.YEAR_OF_PLENTY));
        }

        return deck;
    }

    private Game() {}

    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();

            instance.player = new Player();

        }
        return instance;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void incrementVersionNumber() {
        versionNumber++;
    }

    public Map getMap() {
        return map;
    }

    public Bank getBank(){return bank; }

    public List<Player> getPlayersList(){return playersList; }

    public Player getPlayer(){return player;}

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPlayersList(List<Player> playersList) {
        this.playersList = playersList;
    }

    public void setUser(Player player) {
        this.player = player;
    }

    public int getPlayerTurn() {
        return this.turnTracker.getCurrentTurn();
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;

//        // Marks this Observable object as having been changed; the hasChanged method will now return true.
//        this.setChanged();
//        // If this object has changed, as indicated by the hasChanged method, then notify all of
//        // its observers and then call the clearChanged method to indicate that this object has no longer changed.
//        this.notifyObservers();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PlayerInfo getCurrentPlayerInfo() {
        return currentPlayerInfo;
    }

    public void updateGameInfo(GameInfo[] newGameList) {

        if(this.gameInfo != null) {

            for (int i = 0; i < newGameList.length; i++) {
                if (this.gameInfo.getId() == newGameList[i].getId()) {
                    this.gameInfo = newGameList[i];

                    //update the client's player list as well
                    for(int j = 0; j < newGameList[i].getPlayers().size(); j++)
                    {
                        if(playersList != null) {
                            for (int k = 0; k < this.playersList.size(); k++) {

                                //don't add a player to the player list if he is already in there...
                                if (newGameList[i].getPlayers().get(j).getId() != this.playersList.get(k).getPlayerInfo().getId()) {

                                    PlayerInfo playerInfo = newGameList[i].getPlayers().get(j);
                                    Player p = new Player();
                                    p.setPlayerInfo(playerInfo);

                                    this.playersList.add(p);
                                }
                            }
                        }
                    }

                }
            }
        }

            allGameInfos = newGameList;

            setChanged();
            notifyObservers();

    }

    public void setCurrentPlayerInfo(PlayerInfo currentPlayerInfo) {
        this.currentPlayerInfo = currentPlayerInfo;

    }

    public GameInfo[] getGameInfos() {
        return allGameInfos;
    }

    public void setGameInfos(GameInfo[] gameInfos) {
        this.allGameInfos = gameInfos;
    }

    public TurnTracker getTurnTracker() {
        return turnTracker;
    }

    public void setTurnTracker(TurnTracker turnTracker) {
        this.turnTracker = turnTracker;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

}
