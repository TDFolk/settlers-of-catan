package server.serverModel;

import com.google.gson.Gson;
import decoder.JsonModels.*;

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
        this.players = new JsonPlayer[0];
        this.bank = new JsonBank(24, 24, 24, 24, 24);
        this.winner = -1;
        this.version = 0;
        this.gameID = gameID;
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

    private String getJsonFromModel() {

        Gson gson = new Gson();

        String model = gson.toJson(this);

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
    public String acceptTrade(){return getJsonFromModel();}

    /**changes the data of the model according to the city being built
     *
     * @return the entire model of the game in Json string form
     */
    public String buildCity(){return getJsonFromModel();}

    /**changes the data of the model according to the road being built
     *
     * @return the entire model of the game in Json string form
     */
    public String buildRoad(){return getJsonFromModel();}

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
    public String finishTurn(){return getJsonFromModel();}

    /**changes the data of the model according to the maritime trade being offered
     *
     * @return the entire model of the game in Json string form
     */
    public String maritimeTrade(){return getJsonFromModel();}

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
    public String offerTrade(){return getJsonFromModel();}

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
    public String roll(){return getJsonFromModel();}

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

        JsonPlayer newPlayer = new JsonPlayer();
        newPlayer.setColor(color);
        //newPlayer.

        for(int i = 0; i < players.length; i++)
        {
            if (players[i] == null)
            {

                players[i] =

            }
        }

    }
}
