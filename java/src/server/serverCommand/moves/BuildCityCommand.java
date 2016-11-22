package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.BuildCityObject;
import decoder.JsonModels.JsonLocation;
import decoder.JsonModels.JsonMap;
import decoder.JsonModels.JsonPiece;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;
import shared.locations.VertexLocation;

/**
 * Created by jihoon on 11/7/2016.
 */
public class BuildCityCommand extends Command {
	
	private BuildCityObject buildCityObject;

    public BuildCityCommand(HttpExchange httpExchange) {
        super(httpExchange);
        // In order to create the object it will need to have the proper json from getRequestBody().toString();
        // Do we need to check for cookies here?
        buildCityObject = gson.fromJson(json, BuildCityObject.class);

        // Not sure if this is any different from above...
        // If it is then we can do the others like this
        /*
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        playerIndex = jsonObject.get("playerIndex").getAsInt();
        vertexLocation = fetchVertexLocation(jsonObject.get("vertexLocation").getAsJsonObject());
        */
    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){

            JsonMap jsonMap = ServerModel.getInstance().getGame(super.gameId).getMap();
            jsonMap.addToArray(jsonMap.getCities(), new JsonPiece(null, 0,
                    super.whatDirection(buildCityObject.getVertexLocation().getDir().toString()),
                    new JsonLocation(buildCityObject.getVertexLocation().getHexLoc().getX(),
                            buildCityObject.getVertexLocation().getHexLoc().getY())));

            ServerModel.getInstance().getGame(super.gameId).incrementVersion();
            String response = ServerModel.getInstance().getGame(super.gameId).getJsonFromModel();

            //String response = ServerFacade.getInstance().buildCity(buildCityObject.getPlayerIndex(), buildCityObject.getVertexLocation());
            // The second way from above
            //String response = ServerFacade.getInstance().buildCity(playerIndex, vertexLocation);
            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {
                // Returns the client model JSON (identical to /game/model)
                return new JsonPrimitive(response);
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }
}
