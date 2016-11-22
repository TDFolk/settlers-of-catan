package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.BuildRoadObject;
import decoder.JsonModels.JsonMap;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;
import shared.locations.EdgeLocation;

/**
 * Created by jihoon on 11/7/2016.
 */
public class BuildRoadCommand extends Command {
	
	private int playerIndex;
	private EdgeLocation roadLocation;
	private boolean free;
    private BuildRoadObject buildRoadObject;

    public BuildRoadCommand(HttpExchange httpExchange) {
        super(httpExchange);
        buildRoadObject = gson.fromJson(json, BuildRoadObject.class);
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
            ServerGameModel game = ServerModel.getInstance().getGame(gameId);
            String direction = whatDirection(buildRoadObject.getRoadLocation().getDir().toString());
            JsonMap map = game.getMap();
            String response = game.buildRoad(buildRoadObject.getPlayerIndex(), buildRoadObject.getRoadLocation(),
                    buildRoadObject.isFree(), direction, map);
            game.incrementVersion();

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
