package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.RobPlayerObject;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import shared.locations.HexLocation;

/**
 * Created by jihoon on 11/7/2016.
 */
public class RobCommand extends Command {
	
	private int playerIndex;
	private int victimIndex;
	private HexLocation location;
    private RobPlayerObject robPlayerObject;

    public RobCommand(HttpExchange httpExchange) {
        super(httpExchange);
        robPlayerObject = gson.fromJson(json, RobPlayerObject.class);
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
            String response = ServerFacade.getInstance().robPlayer(robPlayerObject.getPlayerIndex(),
                                                    robPlayerObject.getVictimIndex(), robPlayerObject.getLocation());

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
