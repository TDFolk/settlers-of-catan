package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.BuildRoadObject;
import decoder.JsonModels.JsonLocation;
import decoder.JsonModels.JsonMap;
import decoder.JsonModels.JsonPiece;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;

/**
 * Created by jihoon on 11/7/2016.
 */
public class BuildRoadCommand extends Command {
	
	private int playerIndex;
	private EdgeLocation roadLocation;
	private boolean free;
    private BuildRoadObject buildRoadObject;
    private EdgeDirection edgeDirection;
    private int x;
    private int y;

    public BuildRoadCommand(HttpExchange httpExchange) {
        super(httpExchange);
        json = json.replaceAll("\\n", "");
        buildRoadObject = gson.fromJson(json, BuildRoadObject.class);

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        roadLocation = fetchEdgeLocation(jsonObject.get("roadLocation").getAsJsonObject());

        edgeDirection = roadLocation.getDir();
        x = roadLocation.getHexLoc().getX();
        y = roadLocation.getHexLoc().getY();
    }

    /**
     * This method will handle executing the commands
     *
     * @param json jsonString that will populate the JsonElement
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){
            // TODO isFree needs to be handled.. decrementResources if not free in ServerGameModel
            ServerModel.getInstance().getGame(super.gameId).getMap().setRoads(ServerModel.getInstance().getGame(super.gameId).getMap().addToArray(
                    ServerModel.getInstance().getGame(super.gameId).getMap().getRoads(),
                    new JsonPiece(null, 0, edgeDirection.toString(), new JsonLocation(x, y, edgeDirection.toString())),
                    buildRoadObject.getPlayerIndex()));

            ServerModel.getInstance().getGame(super.gameId).incrementVersion();
            String response = ServerModel.getInstance().getGame(super.gameId).getJsonFromModel();

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
