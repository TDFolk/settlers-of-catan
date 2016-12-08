package server.serverModel;

import com.google.gson.Gson;
import decoder.JsonModels.*;
import model.Facade;
import model.Game;
import model.cards_resources.ResourceCards;
import model.map.Hex;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;

/**Server side model of the data for each individual game
 * Created by bvance on 11/4/2016.
 */
public class ServerGameModel {

    private JsonDeck deck;
    private JsonMap map;
    private JsonPlayer players[];
    private JsonLog log;
    private JsonLog chat;
    private JsonBank bank;
    private JsonTurnTracker turnTracker;
    private JsonTradeOffer tradeOffer;
    private int winner;
    private int version;
    private int gameID;

    public ServerGameModel(int gameID)
    {
        this.deck = new JsonDeck(2, 2, 14, 2, 5);
        this.map = new JsonMap();
        this.chat = new JsonLog();
        this.log = new JsonLog();
        this.players = new JsonPlayer[4];
        this.bank = new JsonBank(24, 24, 24, 24, 24);
        this.winner = -1;
        this.version = 0;
        this.gameID = gameID;
        this.turnTracker = new JsonTurnTracker();
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public JsonDeck getDeck() {
        return deck;
    }

    public void setDeck(JsonDeck deck) {
        this.deck = deck;
    }

    public JsonMap getMap() {
        return map;
    }

    public void setMap(JsonMap map) {
        this.map = map;
    }

    public JsonPlayer[] getPlayers() {
        return players;
    }

    public void setPlayers(JsonPlayer[] players) {
        this.players = players;
    }

    public JsonLog getLog() {
        return log;
    }

    public void setLog(JsonLog log) {
        this.log = log;
    }

    public JsonLog getChat() {
        return chat;
    }

    public void setChat(JsonLog chat) {
        this.chat = chat;
    }

    public JsonBank getBank() {
        return bank;
    }

    public void setBank(JsonBank bank) {
        this.bank = bank;
    }

    public JsonTurnTracker getTurnTracker() {
        return turnTracker;
    }

    public void setTurnTracker(JsonTurnTracker turnTracker) {
        this.turnTracker = turnTracker;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public JsonTradeOffer getTradeOffer() {
        return tradeOffer;
    }

    public void setTradeOffer(JsonTradeOffer tradeOffer) {
        this.tradeOffer = tradeOffer;
    }


    /**
     * Calculates which user, if any, has achieved the award of longest road
     * @return player index of winner
     */
    public int calculateLongestRoad(){return 0;}

    /**
     * Calculates which user, if any, has achieved the award of largest army
     * @return player index of winner
     */
    public int calculateLargestArmy(){return 0;}

    public String getJsonFromModel() {

        Gson gson = new Gson();

        String model = gson.toJson(this);

        model = model.split(",\"gameID\":")[0];

        model = model + "}";

        return model;

    }

    public String gameModel()
    {
        return getJsonFromModel();
    }

    /**changes the data of the model according to the trade accepted
     *
     * @return the entire model of the game in Json string form
     */
    public String acceptTrade(boolean willAccept){

        if(willAccept)
        {
            //exchange the resources from each player

            //sender
            JsonResource senderResult = new JsonResource(players[tradeOffer.getSender()].getResources(), tradeOffer.getOffer(), true);
            players[tradeOffer.getSender()].setResources(senderResult);

            //receiver
            JsonResource receiverResult = new JsonResource(players[tradeOffer.getSender()].getResources(), tradeOffer.getOffer(), false);
            players[tradeOffer.getReceiver()].setResources(receiverResult);

            tradeOffer = null;
        }
        else
        {
            tradeOffer = null;
        }

        incrementVersion();
        return getJsonFromModel();
    }

    public String addAI() {
        //addPlayer("brown", "Foo", 17);
        return getJsonFromModel();
    }

    /**changes the data of the model according to the city being built
     *
     * @return the entire model of the game in Json string form
     */
    public String buildCity(){return getJsonFromModel();}

    /**changes the data of the model according to the road being built
     *
     * @return the entire model of the game in Json string form
     */
    public String buildRoad() {
        return getJsonFromModel();
    }

    /**changes the data of the model according to the settlement being built
     *
     * @return the entire model of the game in Json string form
     */
    public String buildSettlement(){return getJsonFromModel();}

    /**changes the data of the model according to the  dev card being built
     *
     * @return the entire model of the game in Json string form
     */
    public String buyDevCard(){return getJsonFromModel();}

    /**changes the data of the model according to the discard cards
     *
     * @return the entire model of the game in Json string form
     */
    public String discardCards(){return getJsonFromModel();}

    /**changes the data of the model according to the turn being finished
     *
     * @return the entire model of the game in Json string form
     */
    public String finishTurn(){

        if(this.turnTracker.getStatus().equals("FirstRound"))
        {
            if(turnTracker.getCurrentTurn() == 3)
            {
                turnTracker.beginSecondRound();
            }
            else
            {
                turnTracker.incrementTurn();
            }
        }
        else if(this.turnTracker.getStatus().equals("SecondRound"))
        {
            if(turnTracker.getCurrentTurn() == 0)
            {
                turnTracker.beginRollingState();
            }
            else
            {
                turnTracker.decrementTurn();
            }
        }
        else
        {
            turnTracker.incrementTurn();
            turnTracker.beginRollingState();
        }

        incrementVersion();

        return getJsonFromModel();
    }

    /**changes the data of the model according to the maritime trade being offered
     *
     * @return the entire model of the game in Json string form
     * @param playerIndex
     * @param ratio
     * @param inputResource
     * @param outputResource
     */
    public String maritimeTrade(int playerIndex, int ratio, String inputResource, String outputResource)
    {

        JsonResource currentResources = players[playerIndex].getResources();

        switch (inputResource)
        {
            case "brick":
                currentResources.setBrick(currentResources.getBrick() - ratio);
                break;
            case "ore":
                currentResources.setOre(currentResources.getOre() - ratio);
                break;
            case "sheep":
                currentResources.setSheep(currentResources.getSheep() - ratio);
                break;
            case "wheat":
                currentResources.setWheat(currentResources.getWheat() - ratio);
                break;
            case "wood":
                currentResources.setWood(currentResources.getWood() - ratio);
                break;
        }

        switch (outputResource)
        {
            case "brick":
                currentResources.setBrick(currentResources.getBrick() + 1);
                break;
            case "ore":
                currentResources.setOre(currentResources.getOre() + 1);
                break;
            case "sheep":
                currentResources.setSheep(currentResources.getSheep() + 1);
                break;
            case "wheat":
                currentResources.setWheat(currentResources.getWheat() + 1);
                break;
            case "wood":
                currentResources.setWood(currentResources.getWood() + 1);
                break;
        }


        incrementVersion();
        return getJsonFromModel();
    }

    /**changes the data of the model according to the monopoly card being played
     *
     * @return the entire model of the game in Json string form
     */
    public String monopoly(){return getJsonFromModel();}

    /**changes the data of the model according to the monument card being played
     *
     * @return the entire model of the game in Json string form
     */
    public String monument(){return getJsonFromModel();}

    /**changes the data of the model according to the trade being offered
     *
     * @return the entire model of the game in Json string form
     */
    public String offerTrade(int playerIndex, ResourceCards offer, int receiver){

        JsonTradeOffer newTrade = new JsonTradeOffer(playerIndex, offer, receiver);

        tradeOffer = newTrade;
        incrementVersion();
        return getJsonFromModel();

    }

    /**changes the data of the model according to the road building card being played
     *
     * @return the entire model of the game in Json string form
     */
    public String roadBuilding(){return getJsonFromModel();}

    /**changes the data of the model according to the rob taking place
     *
     * @return the entire model of the game in Json string form
     */
    public String rob(){return getJsonFromModel();}

    /**changes the data of the model according to the roll being rolled
     *
     * @return the entire model of the game in Json string form
     */
    public String roll(int numberRolled){

        if(numberRolled == 7)
        {
            int cont = 0;
            for(int i = 0; i < 4; i++){
                if(getPlayers()[i].checkResourceAmount()){
                    cont++;
                }
            }
            if(cont == 0){
                turnTracker.beginRobbingState();
            }
            else{
                turnTracker.beginDiscardingState();
            }
        }
        else
        {
            rewardResources(numberRolled);

            turnTracker.beginPlayingState();
        }

        this.incrementVersion();
        return getJsonFromModel();
    }

    private void rewardResources(int numberRolled)
    {
        //logic for rewarding resources
        for(JsonHex hex : map.getHexes())
        {
            if(hex.getNumber() == numberRolled)
            {
                //check each vertex around this hex and reward those players the resource of the hex
                for(JsonPiece piece : map.getSettlements())
                {
                    if (piece.getHexLocation().getX() == hex.getLocation().getX() &&
                            piece.getHexLocation().getY() == hex.getLocation().getY()) {
                        addResourceFromHexType(getMap().stringToHexType(hex.getResource()), piece.getOwner(), false);
                    }
                    else if (piece.getHexLocation().getNeighborLoc(EdgeDirection.North).getX() == hex.getLocation().getX() &&
                            piece.getHexLocation().getNeighborLoc(EdgeDirection.North).getY() == hex.getLocation().getY()) {
                        addResourceFromHexType(getMap().stringToHexType(hex.getResource()), piece.getOwner(), false);
                    }
                    else {
                        switch(piece.getVertexDirection()) {
                            case NorthEast:
                                if (piece.getHexLocation().getNeighborLoc(EdgeDirection.NorthEast).getX() == hex.getLocation().getX() &&
                                        piece.getHexLocation().getNeighborLoc(EdgeDirection.NorthEast).getY() == hex.getLocation().getY()) {
                                    addResourceFromHexType(getMap().stringToHexType(hex.getResource()), piece.getOwner(), false);
                                }
                                break;
                            case NorthWest:
                                if (piece.getHexLocation().getNeighborLoc(EdgeDirection.NorthWest).getX() == hex.getLocation().getX() &&
                                        piece.getHexLocation().getNeighborLoc(EdgeDirection.NorthWest).getY() == hex.getLocation().getY()) {
                                    addResourceFromHexType(getMap().stringToHexType(hex.getResource()), piece.getOwner(), false);
                                }
                                break;
                        }
                    }
                }

                for(JsonPiece piece : map.getCities())
                {
                    if (piece.getHexLocation().getX() == hex.getLocation().getX() &&
                            piece.getHexLocation().getY() == hex.getLocation().getY()) {
                        addResourceFromHexType(getMap().stringToHexType(hex.getResource()), piece.getOwner(), true);
                    }
                    else if (piece.getHexLocation().getNeighborLoc(EdgeDirection.North).getX() == hex.getLocation().getX() &&
                            piece.getHexLocation().getNeighborLoc(EdgeDirection.North).getY() == hex.getLocation().getY()) {
                        addResourceFromHexType(getMap().stringToHexType(hex.getResource()), piece.getOwner(), true);
                    }
                    else {
                        switch(piece.getVertexDirection()) {
                            case NorthEast:
                                if (piece.getHexLocation().getNeighborLoc(EdgeDirection.NorthEast).getX() == hex.getLocation().getX() &&
                                        piece.getHexLocation().getNeighborLoc(EdgeDirection.NorthEast).getY() == hex.getLocation().getY()) {
                                    addResourceFromHexType(getMap().stringToHexType(hex.getResource()), piece.getOwner(), true);
                                }
                                break;
                            case NorthWest:
                                if (piece.getHexLocation().getNeighborLoc(EdgeDirection.NorthWest).getX() == hex.getLocation().getX() &&
                                        piece.getHexLocation().getNeighborLoc(EdgeDirection.NorthWest).getY() == hex.getLocation().getY()) {
                                    addResourceFromHexType(getMap().stringToHexType(hex.getResource()), piece.getOwner(), true);
                                }
                                break;
                        }
                    }
                }
            }

        }
    }

    public void grabProperResources(HexLocation hexLocation, int currPlayer, VertexDirection vertexDirection, boolean isCity) {
        addResourceFromHexType(getMap().getHexType(hexLocation), currPlayer, isCity);


        addResourceFromHexType(getMap().getHexType(hexLocation.getNeighborLoc(EdgeDirection.North)), currPlayer, isCity);


        switch(vertexDirection) {
            case NorthEast:
                addResourceFromHexType(getMap().getHexType(hexLocation.getNeighborLoc(EdgeDirection.NorthEast)), currPlayer, isCity);
                break;
            case NorthWest:
                addResourceFromHexType(getMap().getHexType(hexLocation.getNeighborLoc(EdgeDirection.NorthWest)), currPlayer, isCity);
                break;
        }
    }

    public boolean addResourceFromHexType(HexType hexType, int currPlayer, boolean isCity) {
        int amount;
        if (isCity) {
            amount = 2;
        }
        else {
            amount = 1;
        }
        switch (hexType) {
            case BRICK:
                players[currPlayer].addResources(new JsonResource(amount, 0, 0, 0, 0));
                return true;
            case ORE:
                players[currPlayer].addResources(new JsonResource(0, 0, 0, 0, amount));
                return true;
            case SHEEP:
                players[currPlayer].addResources(new JsonResource(0, 0, amount, 0, 0));
                return true;
            case WHEAT:
                players[currPlayer].addResources(new JsonResource(0, 0, 0, amount, 0));
                return true;
            case WOOD:
                players[currPlayer].addResources(new JsonResource(0, amount, 0, 0, 0));
                return true;
            default:
                players[currPlayer].addResources(new JsonResource(0, 0, 0, 0, 0));
                return false;
        }
    }

    /**changes the data of the model according to the chat being sent
     *
     * @return the entire model of the game in Json string form
     */
    public String sendChat(){return getJsonFromModel();}

    /**changes the data of the model according to the soldier card being played
     *
     * @return the entire model of the game in Json string form
     */
    public String soldier(){return getJsonFromModel();}

    /**changes the data of the model according to the year of plenty being played
     *
     * @return the entire model of the game in Json string form
     */
    public String yearOfPlenty(){return getJsonFromModel();}

    public boolean addPlayer(String color, String name, int ID) {


        for(int i = 0; i < players.length; i++)
        {
            JsonPlayer newPlayer = new JsonPlayer(color, name, ID, i);

            if (players[i] == null)
            {

                players[i] = newPlayer;
                return true;

            }
        }

        return false;

    }

    public void incrementVersion()
    {
        this.version++;
    }

    public void addLog(String message, String source)
    {
        log.addLine(message,source);
    }

    public void addMessage(String message, String source)
    {
        chat.addLine(message,source);
    }

    public void newLongestRoad(int winner) {

        if(turnTracker.getLongestRoad() != winner) {


            int currentWinner = turnTracker.getLongestRoad();

            if (currentWinner != -1) {
                players[currentWinner].setVictoryPoints(players[currentWinner].getVictoryPoints() - 2);

            }

            turnTracker.setLongestRoad(winner);

            players[winner].incrementPoints();
            players[winner].incrementPoints();
        }

    }

    public void newLargestArmy(int winner)
    {
        if(turnTracker.getLargestArmy() != winner) {


            int currentWinner = turnTracker.getLargestArmy();

            if (currentWinner != -1) {
                players[currentWinner].setVictoryPoints(players[currentWinner].getVictoryPoints() - 2);

            }

            turnTracker.setLongestRoad(winner);

            players[winner].incrementPoints();
            players[winner].incrementPoints();
        }
    }
}
