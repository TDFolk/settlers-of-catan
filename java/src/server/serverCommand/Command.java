package server.serverCommand;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

import java.io.Serializable;

/**The parent class for all commands to be executed on the server side
 * Created by bvanc on 11/4/2016.
 */
public abstract class Command implements Serializable{

    /**
     * Constructor which sets its own httpExchange to the one given as its param
     * @param httpExchange HttpExchange object which will set itself to
     */
    public Command(HttpExchange httpExchange){

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
}
