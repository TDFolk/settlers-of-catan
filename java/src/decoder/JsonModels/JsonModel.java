package decoder.JsonModels;

/**
 * Created by bvanc on 9/30/2016.
 */
public class JsonModel {

    public JsonModel() {
    }

    private JsonDeck deck;
    private JsonMap map;
    private JsonPiece roads[];
    private JsonPiece cities[];
    private JsonPiece settlements[];
    private int radius;
    private JsonPiece ports[];
    private JsonRobber robber;
    private JsonPlayer players[];
    private JsonLog log;
    private JsonLog chat;
    private JsonBank bank;
    private JsonTurnTracker turnTracker;
    private JsonTradeOffer tradeOffer;
    private int winner;
    private int version;

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

    public JsonPiece[] getRoads() {
        return roads;
    }

    public void setRoads(JsonPiece[] roads) {
        this.roads = roads;
    }

    public JsonPiece[] getCities() {
        return cities;
    }

    public void setCities(JsonPiece[] cities) {
        this.cities = cities;
    }

    public JsonPiece[] getSettlements() {
        return settlements;
    }

    public void setSettlements(JsonPiece[] settlements) {
        this.settlements = settlements;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public JsonPiece[] getPorts() {
        return ports;
    }

    public void setPorts(JsonPiece[] ports) {
        this.ports = ports;
    }

    public JsonRobber getRobber() {
        return robber;
    }

    public void setRobber(JsonRobber robber) {
        this.robber = robber;
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
}
