package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.SoldierObject;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import shared.locations.HexLocation;

/**
 * Created by jihoon on 11/7/2016.
 */
public class SoldierCommand extends Command {
	
	private int playerIndex;
	private int victimIndex;
	private HexLocation location;
    private SoldierObject soldierObject;

    public SoldierCommand(HttpExchange httpExchange) {
        super(httpExchange);
        soldierObject = gson.fromJson(json, SoldierObject.class);
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
            String response = ServerFacade.getInstance().soldier(soldierObject.getPlayerIndex(),
                                                    soldierObject.getVictimIndex(), soldierObject.getLocation());

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
