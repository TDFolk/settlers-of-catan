package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.MaritimeTradeObject;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;

/**
 * Created by jihoon on 11/7/2016.
 */
public class MaritimeTradeCommand extends Command {
	
	private int playerIndex;
	private int ratio;
	private String inputResource;
	private String outputResource;
    private MaritimeTradeObject maritimeTradeObject;

    public MaritimeTradeCommand(HttpExchange httpExchange) {
        super(httpExchange);
        maritimeTradeObject = gson.fromJson(json, MaritimeTradeObject.class);
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
            String response = ServerFacade.getInstance().maritimeTrade(maritimeTradeObject.getPlayerIndex(),
                                                    maritimeTradeObject.getRatio(), maritimeTradeObject.getInputResource(),
                                                    maritimeTradeObject.getOutputResource());

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
