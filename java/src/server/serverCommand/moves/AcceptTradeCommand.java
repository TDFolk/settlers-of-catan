package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;
import command.player.AcceptTradeObject;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;

/**
 * Created by jihoon on 11/7/2016.
 */
public class AcceptTradeCommand extends Command {
	
	private AcceptTradeObject acceptTradeObject;

    public AcceptTradeCommand(HttpExchange httpExchange) {
        super(httpExchange);
        acceptTradeObject = gson.fromJson(json, AcceptTradeObject.class);
    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){
            String response = ServerFacade.getInstance().acceptTrade(acceptTradeObject.getPlayerIndex(), acceptTradeObject.isWillAccept());

            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {
                return new JsonPrimitive(gson.toJson(response, ServerGameModel.class));
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }
}
