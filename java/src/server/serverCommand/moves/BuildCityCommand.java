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
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 * Created by jihoon on 11/7/2016.
 */
public class BuildCityCommand extends Command {
	
	private BuildCityObject buildCityObject;
    private VertexLocation vertexLocation;
    private VertexDirection vertexDirection;
    private int x;
    private int y;

    public BuildCityCommand(HttpExchange httpExchange) {
        super(httpExchange);

        json = json.replaceAll("\\n", "");

        buildCityObject = gson.fromJson(json, BuildCityObject.class);

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        vertexLocation = fetchVertexLocation(jsonObject.get("vertexLocation").getAsJsonObject());

        vertexDirection = vertexLocation.getDir();
        x = vertexLocation.getHexLoc().getX();
        y = vertexLocation.getHexLoc().getY();

    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){

            //decrement the resources required to buy a city
            ServerModel.getInstance().getGame(super.gameId).getPlayers()[buildCityObject.getPlayerIndex()].buyCity();

            ServerModel.getInstance().getGame(super.gameId).getMap().setCities(ServerModel.getInstance().getGame(super.gameId).getMap().addToArray(
                    ServerModel.getInstance().getGame(super.gameId).getMap().getCities(),
                    new JsonPiece(null, 0, vertexDirection.toString(), new JsonLocation(x, y, vertexDirection.toString())),
                    buildCityObject.getPlayerIndex()));


            String response = ServerModel.getInstance().getGame(super.gameId).getJsonFromModel();

            //TODO: INCREMENT POINTS HERE; ERASE SETTLEMENT FROM SETTLEMENTS ARRAY???


            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {
                // Returns the client model JSON (identical to /game/model)
                ServerModel.getInstance().getGame(super.gameId).incrementVersion();
                return new JsonPrimitive(response);
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }
}
