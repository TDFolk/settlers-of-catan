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
import server.Server;
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
	
	//private int playerIndex;
	private EdgeLocation roadLocation;
	//private boolean free;
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
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){
            if (buildRoadObject.isFree()) {
                //changed playerIndex to buildRoadObject.getPlayerIndex()
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[buildRoadObject.getPlayerIndex()].decrementRoadTotal();
            }
            else {
                //changed playerIndex to buildRoadObject.getPlayerIndex()
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[buildRoadObject.getPlayerIndex()].buyRoad();
            }

            ServerModel.getInstance().getGame(super.gameId).getMap().setRoads(ServerModel.getInstance().getGame(super.gameId).getMap().addToArray(
                    ServerModel.getInstance().getGame(super.gameId).getMap().getRoads(),
                    new JsonPiece(null, 0, edgeDirection.toString(), new JsonLocation(x, y, edgeDirection.toString())),
                    buildRoadObject.getPlayerIndex()));

            String response = ServerModel.getInstance().getGame(super.gameId).getJsonFromModel();

            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {

                String userName = ServerModel.getInstance().getUsernameFromID(super.playerId);
                String historyMessage = userName + " has built a road";
                ServerModel.getInstance().getGame(super.gameId).addLog(historyMessage, userName);

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
