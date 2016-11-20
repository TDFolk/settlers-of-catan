package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.RoadBuildingObject;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import shared.locations.EdgeLocation;

/**
 * Created by jihoon on 11/7/2016.
 */
public class RoadBuildingCommand extends Command {
	
	private int playerIndex;
	private EdgeLocation spot1;
	private EdgeLocation spot2;
    private RoadBuildingObject roadBuildingObject;

    public RoadBuildingCommand(HttpExchange httpExchange) {
        super(httpExchange);
        roadBuildingObject = gson.fromJson(json, RoadBuildingObject.class);
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
            String response = ServerFacade.getInstance().roadBuilding(roadBuildingObject.getPlayerIndex(),
                                                    roadBuildingObject.getSpot1(), roadBuildingObject.getSpot2());

            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {
                // Returns the client model JSON (identical to /game/model)
                return new JsonPrimitive(gson.toJson(response, ServerGameModel.class));
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }
}
