package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.SendChatObject;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;

/**
 * Created by jihoon on 11/7/2016.
 */
public class SendChatCommand extends Command {
	
	private int playerIndex;
	private String content;
    private SendChatObject sendChatObject;

    public SendChatCommand(HttpExchange httpExchange) {
        super(httpExchange);
        sendChatObject = gson.fromJson(json, SendChatObject.class);
    }

    /**
     * This method will handle executing the commands
     *
     * @param json jsonString that will populate the JsonElement
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {

        String userName = ServerModel.getInstance().getUsernameFromID(super.playerId);

        if(super.hasGameCookie && super.hasUserCookie){

            ServerModel.getInstance().getGame(super.gameId).incrementVersion();

            String response = ServerFacade.getInstance().sendChat(userName, super.gameId,
                                                    sendChatObject.getContent());

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
