package server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

import java.util.Set;
import java.util.TreeSet;

/**
 * This interface provides the means of communicating with the server back and forth.
 * Created by jihoon on 9/16/2016.
 */
public interface IServer {

    /**
     * Logs the caller in to the server, sets their catan.user HTTP cookie
     * @pre username not null, password not null
     * @post if successful, server returns 200 HTTP success response, HTTP response headers set the catan.user cookie
     *          if invalid, returns a 400 HTTP response with an error message
     * @param username String username of player
     * @param password String password of player
     * @return success of login
     */
    public boolean userLogin(String username, String password);

    /**
     * Creates a new user account, logs the caller in to server as a new user, and sets their catan.user HTTP cookie
     * @pre username not null, password not null
     * @post if successful, server returns 200 HTTP success response and new account gets created
     *          HTTP response headers set the catan.user cookie
     *          if invalid, returns a 400 HTTP response with an error message
     * @param username String username of player
     * @param password String password of player
     * @return success of registration
     */
    public boolean userRegister(String username, String password);

    /**
     * Returns information about all the current games on the server
     * @pre None
     * @post if successful, server returns 200 HTTP success response, and body contains a JSON array with information of games
     *          if invalid, returns a 400 HTTP response with an error message
     * @return JSONArray containing a list of objects about the server's games
     */
    public JsonArray gameList();

    /**
     * Creates a new game on the server
     * @pre gameName is not null, randomTiles, randomNumbers, and randomPorts contain valid boolean values
     * @post if successful, server returns 200 HTTP success response, new game gets created with specified properties
     *          body containing a JSON object describing the newly created game
     *          if invalid, returns a 400 HTTP response with an error message
     * @param gameName String gameName of the new game
     * @return a JSON object containing properties of the newly created game
     */
    public JsonObject gameCreate(String gameName);

    /**
     * Adds the player to the specified game and sets their catan.game cookie
     * @pre valid catan.user HTTP cookie
     *          may join game: already in the game OR there is space in the game to add a new player
     *          specified game ID is valid
     *          specified color is valid
     * @post if successful, server returns 200 HTTP success response, player is in game with specified color
     *          server response includes the "Set-cookie" response header settings the catan.game HTTP cookie
     *          if invalid, returns a 400 HTTP response with an error message
     * @param userCookie String userCookie is the response header
     * @param gameID unique identifier for a particular game
     * @param color player color
     * @return success of joining the game
     */
    public boolean gameJoin(String userCookie, int gameID, CatanColor color);

    /**
     * Returns the current state of the game in JSON format and version number
     * @pre valid catan.user and catan.game HTTP cookies
     *          version number is included as the "version" query parameter in requested URL, value is valid integer
     * @post if successful, server returns 200 HTTP success response, response body contains JSON data
     *          full client model JSON is returned if the caller does not provide a version number
     *          "true" is returned if the caller provided a version number and the version number matched the version on server
     *          if invalid, returns a 400 HTTP response with an error message
     * @param versionNumber version number of the game
     * @return JSON object of the model
     */
    public JsonObject gameModelVersion(int versionNumber);

    /**
     * Returns a list of supported AI player types (LARGEST_ARMY is the only supported type currently)
     * @pre none
     * @post if successful, server returns 200 HTTP success response
     *          body contains a JSON string array enumerating the different types of AI players
     * @return list of supported AI player types
     */
    public JsonArray gameListAI();

    /**
     * Adds an AI player to the current game
     * @pre valid catan.user and catan.game HTTP cookies, space in the game for another player, "AIType" is valid
     * @post if successful, server returns 200 HTTP success response, new AI player of type has been added to the game
     *          server selected a name and color for the player
     *          if invalid, returns a 400 HTTP response with an error message
     * @param typeAI type of the AI being added to the game from the listAI
     * @return success of adding an AI
     */
    public boolean gameAddAI(String typeAI);

    // Anytime Commands -----

    /**
     * Posts a message to the chat
     * @pre None
     * @post The chat contains your message at the end
     * @param content the message you want to send
     * @return success of if the chat contains your message
     */
    public boolean sendChat(String content);

    // Miscellaneous Commands -----

    /**
     * Determines if a trade will be accepted or not
     * @pre You have been offered a trade;
     *          you have the required resources
     * @post If accepted, you and the player who offered swap the specified resources;
     *          if you decline, no resources are exchanged;
     *          the trade offer is removed
     * @param willAccept Whether or not you accept the offered trade
     * @return if the trade was accepted or not
     */
    public boolean acceptTrade(boolean willAccept);

    /**
     * Discards the desired cards from the resource hand
     * @pre The status of the client model is 'Discarding';
     *          you have over 7 cards;
     *          you have the cards you're choosing to discard
     * @post You gave up the specified resources;
     *          if you're the last one to discard, the client model status changes to 'Robbing'
     * @param discardedCards the cards you are discarding
     * @return success of discarded cards
     */
    public boolean discardCards(JsonObject discardedCards);

    // Rolling Commands -----

    /**
     * Rolls the dice
     * @pre It is your turn;
     *          the number is in the range 2-12;
     *          the client model's status is 'Rolling'
     * @post The client model's status is now in 'Discarding' or 'Robbing' or 'Playing'
     * @param number the number you rolled
     * @return success of the roll
     */
    public boolean rollNumber(int number);

    // Playing Commands -----

    /**
     * Builds a road
     * @pre The road location is open;
     *          the road location is connected to another road owned by the player;
     *          the road location is not on water;
     *          you have the required resources(1 wood, 1 brick = 1 road);
     *          Setup round: Must be placed by settlement owned by the player with no adjacent road
     * @post You lost the resources required to build a road;
     *          the road is on the map at the specified location;
     *          if applicable, "longest road" is awarded
     * @param isFree whether or not you get this piece for free (i.e., setup)
     * @param roadLocation the new road's location
     * @return success of built road
     */
    public boolean buildRoad(boolean isFree, EdgeLocation roadLocation);

    /**
     * Builds a settlement
     * @pre The settlement location is open;
     *          the settlement location is not on water;
     *          the settlement location is connected to one of your roads, exept during setup;
     *          you have the required resources(1 wood, 1 brick, 1 wheat, 1 sheep = 1 settlement)
     * @post you lost the resources required to build a settlement;
     *          the settlement is on the map at the specified location
     * @param isFree whether or not you get this piece for free(i.e, setup)
     * @param vertexLocation the location of the settlement
     * @return success of build settlement
     */
    public boolean buildSettlement(boolean isFree, VertexLocation vertexLocation);

    /**
     * Builds a city
     * @pre The city location is where you currently have a settlement;
     *          you have the required resources(2 wheat, 3 ore = 1 city)
     * @post You lost the resources required to build a city;
     *          the city is on the map at the specified location;
     *          you got a settlement back
     * @param vertexLocation the location of the city
     * @return success of build city
     */
    public boolean buildCity(VertexLocation vertexLocation);

    /**
     * Proposes a trade
     * @pre you have the resources you are offering
     * @post the trade is offered to the other player(stored in the server model)
     * @param offer
     * @param playerIndex
     * @return
     */
    public boolean offerTrade(JsonObject offer, int playerIndex);


    /**
     * Trades with a port
     * @pre You have the resources you are giving;
     *          for ratios less than 4, you have the correct port for the trade
     * @post The trade has been executed;
     *          the offered resources are in the bank, and the requested resource has been received
     * @param ratio trade ratio
     * @param inputResource what you are giving
     * @param outputResource what you are getting
     * @return success of a maritime trade
     */
    public boolean maritimeTrade(int ratio, ResourceType inputResource, ResourceType outputResource);



}
