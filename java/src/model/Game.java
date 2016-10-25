package model;

import client.data.GameInfo;
import client.data.PlayerInfo;
import decoder.JsonModels.*;
import model.cards_resources.Bank;
import model.cards_resources.DevelopmentCard;
import model.cards_resources.ResourceCards;
import model.cards_resources.Trade;
import model.map.Hex;
import model.map.Map;
import model.map.Port;
import model.pieces.Building;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.HexType;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;

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

    private int versionNumber = 0;
    private Bank bank;
    private List<Message> chat;
    private List<Message> log;
    private Map map;
    private List<Player> playersList;
    private Player player;
    private Trade activeTrade; //null if none is ongoing
    private TurnTracker turntracker;
    private Player winner;
    private GameInfo gameInfo;


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

        ArrayList<Message> chat = createMessageList(model.getChat().getLines());
        ArrayList<Message> log = createMessageList(model.getLog().getLines());

        this.log = log;
        this.chat = chat;

        //TODO: Finish implementing this method to fill the map data member and below.

        //replace map
        ArrayList<Hex> hexes = createHexList(model.getMap().getHexes());
        ArrayList<Building> buildings = createBuildingList(model.getCities(), model.getSettlements(), model.getPlayers());
        //ArrayList<Road> roads = createRoadList(model.getRoads());
        //ArrayList<Port> ports = createPortList(model.getPorts());

        //this.map = new Map(hexes, buildings,roads, ports);

        //replace Lsit<Players> players



        //replace Player

        //replace activeTrade

        //replace turnTracker

        //replace winner

        //update gameState?


        // Marks this Observable object as having been changed; the hasChanged method will now return true.
        this.setChanged();
        // If this object has changed, as indicated by the hasChanged method, then notify all of
        // its observers and then call the clearChanged method to indicate that this object has no longer changed.
        this.notifyObservers();

    }

    public ArrayList<Hex> createHexList(JsonHex[] hexes)
    {
        ArrayList<Hex> hexList = new ArrayList<>();

        for(int i = 0; i < hexes.length; i++)
        {

            HexType type;

            switch(hexes[i].getResource())
            {
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

        for(int i = 0; i < cities.length; i++)
        {
            HexLocation hexLocation = new HexLocation(cities[i].getLocation().getX(), cities[i].getLocation().getY());

            CatanColor color;

            for(int j = 0; i < players.length; i++)
            {
                if(players[i].getPlayerIndex() == cities[i].getOwner())
                {
                    switch (players[i].getColor())
                    {
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

            VertexDirection dir;

            switch(cities[i].getLocation().getDirection())
            {
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

            //Building newBuilding = new City(color, dir);
            //buildings.add();
        }
        return buildings;

    }

    public ArrayList<Message> createMessageList(JsonLine[] lines)
    {

        ArrayList<Message> messageList = new ArrayList<>();

        for(int i = 0; i < lines.length; i++)
        {
            messageList.add(new Message(lines[i].getMessage(), lines[i].getSource()));
        }

        return messageList;

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
        }
        return instance;
    }

    /**
     * Initialize/reinitialize all of the data in the model from the info returned by the server
     */
    public void initializeData(int versionNumber, Bank bank, List<Message> chat, List<Message> log, Map map,
                               List<Player> playersList, Player player, Trade activeTrade, TurnTracker turnTracker, Player winner) {
        this.versionNumber = versionNumber;
        this.bank = bank;
        this.chat = chat;
        this.log = log;
        this.map = map;
        this.playersList = playersList;
        this.player = player;
        this.activeTrade = activeTrade; //null if none is ongoing
        this.turntracker = turnTracker;
        this.winner = winner;
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

    public Player getPlayerTurn() {
        return this.turntracker.getPlayerTurn();
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }


}
