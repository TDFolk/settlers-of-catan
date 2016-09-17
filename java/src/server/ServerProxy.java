package server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.org.apache.xpath.internal.operations.String;
import shared.definitions.CatanColor;

/**
 * The ServerProxy acts as the 'inbewteen' for the client and the server. Sends requests to and receives data from
 * the server to give to the client
 * Created by jihoon on 9/16/2016.
 */
public class ServerProxy implements IServer {

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
    public ServerProxy(String port, String host){

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
    public boolean userLogin(String username, String password) {
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
    public boolean userRegister(String username, String password) {
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
    public JsonObject gameCreate(String gameName) {
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
    public boolean gameJoin(String userCookie, int gameID, CatanColor color) {
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
        return null;
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
    public boolean gameAddAI(String typeAI) {
        return false;
    }
}
