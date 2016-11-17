package server.serverCommand;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import server.IServer;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**The parent class for all commands to be executed on the server side
 * Created by bvanc on 11/4/2016.
 */

public abstract class Command implements Serializable{

    protected HttpExchange exchange;

    protected int gameId;
    protected int playerId;
    protected String json;

    protected boolean hasUserCookie = false;
    protected boolean hasGameCookie = false;

    public static IServer serverFacade;

    public static void setServer(IServer server)
    {
        serverFacade = server;
    }

    /**
     * Constructor which sets its own httpExchange to the one given as its param
     * @param httpExchange HttpExchange object which will set itself tox
     */
    public Command(HttpExchange httpExchange){
        exchange = httpExchange;

        //... test what this does?
        String foo = exchange.getRequestMethod();

        //grab the json
        json = exchange.getRequestBody().toString();

        Headers headers = exchange.getRequestHeaders();
        List<String> cookies = headers.get("Cookie");

        if(cookies != null) {
            String catanCookie = cookies.get(cookies.size() - 1);
            try{
                parseCookie(catanCookie);
            }
            catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }


    }

    /**
     * This method adds a command to the provider
     */
    public void addCommand(){
    }

    /**
     * This method extracts the vertex location out of a json object
     * @param jsonObject the json object which holds a vertex location
     * @return returns a vertex location from the given json object
     */
    public VertexLocation fetchVertexLocation(JsonObject jsonObject){
       return null;
    }

    /**
     * This method extracts the edge location out of a json object
     * @param jsonObject the json object which holds a edge location
     * @return returns a edge location from the given json object
     */
    public EdgeLocation fetchEdgeLocation(JsonObject jsonObject){
        return null;
    }

    /**
     * This method sets the game id
     * @param id the id to set the game to
     */
    public void setGameID(int id){

    }

    /**
     * This method sets the player id
     * @param id the id to set the player to
     */
    public void setPlayerID(int id){

    }

    /**
     * This method will handle the serialization of objects using BAOS
     * @return returns the serialized object
     */
    public String serialize(){
        return null;
    }

    /**
     * This method will handle the deserialization of objects, creating new gson
     * @param string the string that will be made into a new gson
     */
    public void deserialize(String string){
    }


    /**
     * This method will handle executing the commands
     * @return returns a json element from the given jsonString
     */
    public JsonElement execute(){
        return null;
    }


    //parse the freaking cookie
    private void parseCookie(String cookie) throws UnsupportedEncodingException{
        String[] params = cookie.split(";");

        for(String string : params){
            //for catanUser Cookie
            if(string.contains("catan.user")){
                String decode = URLDecoder.decode(string, "UTF-8");
                String idBit = decode.substring(decode.indexOf("playerID"));
                String playerID = idBit.substring(idBit.indexOf(":") + 1, idBit.indexOf("}"));

                this.playerId = Integer.parseInt(playerID);
                this.hasUserCookie = true;
            }
            //for catanGame Cookie
            else if(string.contains("catan.game")){
                String decode = URLDecoder.decode(string, "UTF-8");
                String gameIdBit = decode.substring(decode.indexOf("=") + 1);
                gameIdBit = gameIdBit.replace("~Path=/~", "");
                if(gameIdBit != null && !gameIdBit.equals("") && !gameIdBit.equals("null")) {
                    this.gameId = Integer.parseInt(gameIdBit);
                    this.hasGameCookie = true;
                }
            }
        }

    }
}
