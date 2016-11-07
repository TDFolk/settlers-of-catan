package server.serverModel;

import decoder.JsonModels.*;

/**Server side model of the data for every game
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


    //longest road
    //largest army
    public int calculateLongestRoad(){return 0;}

    public int calculateLargestArmy(){return 0;}

    private String getJsonFromModel() {

        return "model";

    }

    public String acceptTrade(){return getJsonFromModel();}

    public String buildCity(){return getJsonFromModel();}

    public String buildRoad(){return getJsonFromModel();}

    public String buildSettlement(){return getJsonFromModel();}

    public String buyDevCard(){return getJsonFromModel();}

    public String discardCards(){return getJsonFromModel();}

    public String finishTurn(){return getJsonFromModel();}

    public String maritimeTrade(){return getJsonFromModel();}

    public String monopoly(){return getJsonFromModel();}

    public String monument(){return getJsonFromModel();}

    public String offerTrade(){return getJsonFromModel();}

    public String roadBuilding(){return getJsonFromModel();}

    public String rob(){return getJsonFromModel();}

    public String roll(){return getJsonFromModel();}

    public String sendChat(){return getJsonFromModel();}

    public String soldier(){return getJsonFromModel();}

    public String yearOfPlenty(){return getJsonFromModel();}

}
