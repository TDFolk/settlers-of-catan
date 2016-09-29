package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.IServer;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * This class is for testing the ServerProxy and its server commands with hard-coded JSON objects and arrays.
 * Created by jihoon on 9/16/2016.
 */
public class MockServer implements IServer {


    /**
     * @param port port id used to connect to the server
     * @param host host id used to connect to the server
     */
    private String port;
    private String host;

    /**
     *
     * @param port port id used to connect to the server
     * @param host host id used to connect to the server
     */
    public MockServer(String port, String host){

        this.port = port;
        this.host = host;

    }


    /**
     * Logs the caller in to the server, sets their catan.user HTTP cookie
     *
     * @param username String username of player
     * @param password String password of player
     * @return success of login
     * @pre username not null, password not null
     * @post if successful, server returns 200 HTTP success response, HTTP response headers set the catan.user cookie
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public boolean userLogin(java.lang.String username, java.lang.String password) {
        return false;
    }

    /**
     * Creates a new user account, logs the caller in to server as a new user, and sets their catan.user HTTP cookie
     *
     * @param username String username of player
     * @param password String password of player
     * @return success of registration
     * @pre username not null, password not null
     * @post if successful, server returns 200 HTTP success response and new account gets created
     * HTTP response headers set the catan.user cookie
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public boolean userRegister(java.lang.String username, java.lang.String password) {
        return false;
    }

    /**
     * Returns information about all the current games on the server
     *
     * @return JSONArray containing a list of objects about the server's games
     * @pre None
     * @post if successful, server returns 200 HTTP success response, and body contains a JSON array with information of games
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public JsonArray gameList() {
        return null;
    }

    /**
     * Creates a new game on the server
     *
     * @param gameName String gameName of the new game
     * @return a JSON object containing properties of the newly created game
     * @pre gameName is not null, randomTiles, randomNumbers, and randomPorts contain valid boolean values
     * @post if successful, server returns 200 HTTP success response, new game gets created with specified properties
     * body containing a JSON object describing the newly created game
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public JsonObject gameCreate(java.lang.String gameName) {
        return null;
    }

    /**
     * Adds the player to the specified game and sets their catan.game cookie
     *
     * @param userCookie String userCookie is the response header
     * @param gameID     unique identifier for a particular game
     * @param color      player color
     * @return success of joining the game
     * @pre valid catan.user HTTP cookie
     * may join game: already in the game OR there is space in the game to add a new player
     * specified game ID is valid
     * specified color is valid
     * @post if successful, server returns 200 HTTP success response, player is in game with specified color
     * server response includes the "Set-cookie" response header settings the catan.game HTTP cookie
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public boolean gameJoin(java.lang.String userCookie, int gameID, CatanColor color) {
        return false;
    }

    /**
     * Returns the current state of the game in JSON format and version number
     *
     * @param versionNumber version number of the game
     * @return JSON object of the model
     * @pre valid catan.user and catan.game HTTP cookies
     * version number is included as the "version" query parameter in requested URL, value is valid integer
     * @post if successful, server returns 200 HTTP success response, response body contains JSON data
     * full client model JSON is returned if the caller does not provide a version number
     * "true" is returned if the caller provided a version number and the version number matched the version on server
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public JsonObject gameModelVersion(int versionNumber) {

        JsonParser parser = new JsonParser();
        JsonObject newModel = parser.parse(jsonModelExample).getAsJsonObject();

        return newModel;
    }

    /**
     * Returns a list of supported AI player types (LARGEST_ARMY is the only supported type currently)
     *
     * @return list of supported AI player types
     * @pre none
     * @post if successful, server returns 200 HTTP success response
     * body contains a JSON string array enumerating the different types of AI players
     */
    @Override
    public JsonArray gameListAI() {
        return null;
    }

    /**
     * Adds an AI player to the current game
     *
     * @param typeAI type of the AI being added to the game from the listAI
     * @return success of adding an AI
     * @pre valid catan.user and catan.game HTTP cookies, space in the game for another player, "AIType" is valid
     * @post if successful, server returns 200 HTTP success response, new AI player of type has been added to the game
     * server selected a name and color for the player
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public boolean gameAddAI(java.lang.String typeAI) {
        return false;
    }

    /**
     * Posts a message to the chat
     *
     * @param content the message you want to send
     * @return success of if the chat contains your message
     * @pre None
     * @post The chat contains your message at the end
     */
    @Override
    public boolean sendChat(java.lang.String content) {
        return false;
    }

    /**
     * Determines if a trade will be accepted or not
     *
     * @param willAccept Whether or not you accept the offered trade
     * @return if the trade was accepted or not
     * @pre You have been offered a trade;
     * you have the required resources
     * @post If accepted, you and the player who offered swap the specified resources;
     * if you decline, no resources are exchanged;
     * the trade offer is removed
     */
    @Override
    public boolean acceptTrade(boolean willAccept) {
        return false;
    }

    /**
     * Discards the desired cards from the resource hand
     *
     * @param discardedCards the cards you are discarding
     * @return success of discarded cards
     * @pre The status of the client model is 'Discarding';
     * you have over 7 cards;
     * you have the cards you're choosing to discard
     * @post You gave up the specified resources;
     * if you're the last one to discard, the client model status changes to 'Robbing'
     */
    @Override
    public boolean discardCards(JsonObject discardedCards) {
        return false;
    }

    /**
     * Rolls the dice
     *
     * @param number the number you rolled
     * @return success of the roll
     * @pre It is your turn;
     * the number is in the range 2-12;
     * the client model's status is 'Rolling'
     * @post The client model's status is now in 'Discarding' or 'Robbing' or 'Playing'
     */
    @Override
    public boolean rollNumber(int number) {
        return false;
    }

    /**
     * Builds a road
     *
     * @param isFree       whether or not you get this piece for free (i.e., setup)
     * @param roadLocation the new road's location
     * @return success of built road
     * @pre The road location is open;
     * the road location is connected to another road owned by the player;
     * the road location is not on water;
     * you have the required resources(1 wood, 1 brick = 1 road);
     * Setup round: Must be placed by settlement owned by the player with no adjacent road
     * @post You lost the resources required to build a road;
     * the road is on the map at the specified location;
     * if applicable, "longest road" is awarded
     */
    @Override
    public boolean buildRoad(boolean isFree, EdgeLocation roadLocation) {
        return false;
    }

    /**
     * Builds a settlement
     *
     * @param isFree         whether or not you get this piece for free(i.e, setup)
     * @param vertexLocation the location of the settlement
     * @return success of build settlement
     * @pre The settlement location is open;
     * the settlement location is not on water;
     * the settlement location is connected to one of your roads, exept during setup;
     * you have the required resources(1 wood, 1 brick, 1 wheat, 1 sheep = 1 settlement)
     * @post you lost the resources required to build a settlement;
     * the settlement is on the map at the specified location
     */
    @Override
    public boolean buildSettlement(boolean isFree, VertexLocation vertexLocation) {
        return false;
    }

    /**
     * Builds a city
     *
     * @param vertexLocation the location of the city
     * @return success of build city
     * @pre The city location is where you currently have a settlement;
     * you have the required resources(2 wheat, 3 ore = 1 city)
     * @post You lost the resources required to build a city;
     * the city is on the map at the specified location;
     * you got a settlement back
     */
    @Override
    public boolean buildCity(VertexLocation vertexLocation) {
        return false;
    }

    /**
     * Proposes a trade
     *
     * @param offer    negative numbers mean you get those cards
     * @param receiver the recipient of the trade offer(playerIndex)
     * @return
     * @pre you have the resources you are offering
     * @post the trade is offered to the other player(stored in the server model)
     */
    @Override
    public boolean offerTrade(JsonObject offer, int receiver) {
        return false;
    }

    /**
     * Trades with a port
     *
     * @param ratio          trade ratio
     * @param inputResource  what you are giving
     * @param outputResource what you are getting
     * @return success of a maritime trade
     * @pre You have the resources you are giving;
     * for ratios less than 4, you have the correct port for the trade
     * @post The trade has been executed;
     * the offered resources are in the bank, and the requested resource has been received
     */
    @Override
    public boolean maritimeTrade(int ratio, ResourceType inputResource, ResourceType outputResource) {
        return false;
    }

    /**
     * Robs a player
     *
     * @param location    the new robber location
     * @param victimIndex -1 if not robbing anyone, else, player you are robbing(playerIndex)
     * @return success of a robbed player
     * @pre The robber is not being kept in the same location;
     * if a player is being robbed(i.e., victimIndex != 1), the player
     * being robbed has resource cards
     * @post the robber is in the new location;
     * the player is being robbed(if any) gave you one of his resource cards(randomly selected)
     */
    @Override
    public boolean robPlayer(HexLocation location, int victimIndex) {
        return false;
    }

    /**
     * @pre None(except the preconditions for the 'Playing' section)
     * @post The cards in your new dev card hand have been transferred to your old dev card hand;
     * it is the next player's turn
     */
    @Override
    public void finishTurn() {

    }

    /**
     * @pre You have the required resources(1 ore, 1 wheat, 1 sheep)
     * there are dev cards left in the deck
     * @post You have a new card;
     * if it is a monument card, it has been added to your old dev card hand;
     * if it is a non-monument card, it has been added to your new dev card
     * hand (unplayable this turn)
     */
    @Override
    public void buyDevCard() {

    }

    /**
     * What happens when a soldier dev card is played
     *
     * @param location    the new robber location
     * @param victimIndex playerIndex, or -1 if you are not robbing anyone
     * @return success of played soldier dev card
     * @pre The robber is not being kept in the same location;
     * if a player is being robbed(i.e., victimIndex != -1), the player
     * being robbed has resource cards
     * @post The robber is in the new location;
     * the player being robbed(if any) gave you one of his resource cards (randomly selected);
     * if applicable, "largest army" is awarded;
     * you are not allowed to play other dev cards during this turn(except monument)
     */
    @Override
    public boolean soldier(HexLocation location, int victimIndex) {
        return false;
    }

    /**
     * What happens when a year of plenty dev card is played
     *
     * @param resource1 the first resource you want to receive
     * @param resource2 the second resource you want to recieve
     * @return success of a played year of plenty dev card
     * @pre the two specified resources are in the bank
     * @post you gained the two specified resources
     */
    @Override
    public boolean yearOfPlenty(ResourceType resource1, ResourceType resource2) {
        return false;
    }

    /**
     * What happens when a road building dev card is played
     *
     * @param spot1 first road location
     * @param spot2 second road location
     * @return success of a played road building dev card
     * @pre The first road location(spot1) is connected to one of your roads;
     * the second road location(spot2) is connected to one of your roads or to
     * the first road location(spot1);
     * neither road location is on water;
     * you have at least two unused roads
     * @post You have two fewer unused roads;
     * two new roads appear on the map at the specified locations;
     * if applicable, "longest road" has been awarded
     */
    @Override
    public boolean roadBuilding(EdgeLocation spot1, EdgeLocation spot2) {
        return false;
    }

    /**
     * What happens when a monopoly dev card is played
     *
     * @param resource the resource being taken from the other players
     * @return success of a played monopoly dev card
     * @pre None(except the general preconditions)
     * @post All of the other players have given you all of their resource cards of the specified type
     */
    @Override
    public boolean monopoly(ResourceType resource) {
        return false;
    }

    /**
     * What happens when a monument dev card is played
     *
     * @pre You have enough monument cards to win the game(10 victory points)
     * @post you gained a victory point
     */
    @Override
    public void monument() {

    }

    private String jsonModelExample = "{\n"+
            "  \"bank\": {\n"+
            "    \"brick\": \"integer\",\n"+
            "    \"ore\": \"integer\",\n"+
            "    \"sheep\": \"integer\",\n"+
            "    \"wheat\": \"integer\",\n"+
            "    \"wood\": \"integer\"\n"+
            "  },\n"+
            "  \"chat\": {\n"+
            "    \"lines\": [\n"+
            "      {\n"+
            "        \"message\": \"string\",\n"+
            "        \"source\": \"string\"\n"+
            "      }\n"+
            "    ]\n"+
            "  },\n"+
            "  \"log\": {\n"+
            "    \"lines\": [\n"+
            "      {\n"+
            "        \"message\": \"string\",\n"+
            "        \"source\": \"string\"\n"+
            "      }\n"+
            "    ]\n"+
            "  },\n"+
            "  \"map\": {\n"+
            "    \"hexes\": [\n"+
            "      {\n"+
            "        \"location\": {\n"+
            "          \"x\": \"integer\",\n"+
            "          \"y\": \"integer\"\n"+
            "        },\n"+
            "        \"resource\": \"string\",\n"+
            "        \"number\": \"integer\"\n"+
            "      }\n"+
            "    ],\n"+
            "    \"ports\": [\n"+
            "      {\n"+
            "        \"resource\": \"string\",\n"+
            "        \"location\": {\n"+
            "          \"x\": \"integer\",\n"+
            "          \"y\": \"integer\"\n"+
            "        },\n"+
            "        \"direction\": \"string\",\n"+
            "        \"ratio\": \"integer\"\n"+
            "      }\n"+
            "    ],\n"+
            "    \"roads\": [\n"+
            "      {\n"+
            "        \"owner\": \"index\",\n"+
            "        \"location\": {\n"+
            "          \"x\": \"integer\",\n"+
            "          \"y\": \"integer\",\n"+
            "          \"direction\": \"string\"\n"+
            "        }\n"+
            "      }\n"+
            "    ],\n"+
            "    \"settlements\": [\n"+
            "      {\n"+
            "        \"owner\": \"index\",\n"+
            "        \"location\": {\n"+
            "          \"x\": \"integer\",\n"+
            "          \"y\": \"integer\",\n"+
            "          \"direction\": \"string\"\n"+
            "        }\n"+
            "      }\n"+
            "    ],\n"+
            "    \"cities\": [\n"+
            "      {\n"+
            "        \"owner\": \"index\",\n"+
            "        \"location\": {\n"+
            "          \"x\": \"integer\",\n"+
            "          \"y\": \"integer\",\n"+
            "          \"direction\": \"string\"\n"+
            "        }\n"+
            "      }\n"+
            "    ],\n"+
            "    \"radius\": \"integer\",\n"+
            "    \"robber\": {\n"+
            "      \"x\": \"integer\",\n"+
            "      \"y\": \"integer\"\n"+
            "    }\n"+
            "  },\n"+
            "  \"players\": [\n"+
            "    {\n"+
            "      \"cities\": \"index\",\n"+
            "      \"color\": \"string\",\n"+
            "      \"discarded\": \"boolean\",\n"+
            "      \"monuments\": \"index\",\n"+
            "      \"name\": \"string\",\n"+
            "      \"newDevCards\": {\n"+
            "        \"monopoly\": \"index\",\n"+
            "        \"monument\": \"index\",\n"+
            "        \"roadBuilding\": \"index\",\n"+
            "        \"soldier\": \"index\",\n"+
            "        \"yearOfPlenty\": \"index\"\n"+
            "      },\n"+
            "      \"oldDevCards\": {\n"+
            "        \"monopoly\": \"index\",\n"+
            "        \"monument\": \"index\",\n"+
            "        \"roadBuilding\": \"index\",\n"+
            "        \"soldier\": \"index\",\n"+
            "        \"yearOfPlenty\": \"index\"\n"+
            "      },\n"+
            "      \"playerIndex\": \"index\",\n"+
            "      \"playedDevCard\": \"boolean\",\n"+
            "      \"playerID\": \"integer\",\n"+
            "      \"resources\": {\n"+
            "        \"brick\": \"integer\",\n"+
            "        \"ore\": \"integer\",\n"+
            "        \"sheep\": \"integer\",\n"+
            "        \"wheat\": \"integer\",\n"+
            "        \"wood\": \"integer\"\n"+
            "      },\n"+
            "      \"roads\": \"index\",\n"+
            "      \"settlements\": \"integer\",\n"+
            "      \"soldiers\": \"integer\",\n"+
            "      \"victoryPoints\": \"integer\"\n"+
            "    }\n"+
            "  ],\n"+
            "  \"tradeOffer\": {\n"+
            "    \"sender\": \"integer\",\n"+
            "    \"receiver\": \"integer\",\n"+
            "    \"offer\": {\n"+
            "      \"brick\": \"integer\",\n"+
            "      \"ore\": \"integer\",\n"+
            "      \"sheep\": \"integer\",\n"+
            "      \"wheat\": \"integer\",\n"+
            "      \"wood\": \"integer\"\n"+
            "    }\n"+
            "  },\n"+
            "  \"turnTracker\": {\n"+
            "    \"currentTurn\": \"index\",\n"+
            "    \"status\": \"string\",\n"+
            "    \"longestRoad\": \"index\",\n"+
            "    \"largestArmy\": \"index\"\n"+
            "  },\n"+
            "  \"version\": \"index\",\n"+
            "  \"winner\": \"index\"\n"+
            "}\n";
}
