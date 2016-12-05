package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;
import command.player.RollNumberObject;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;

/**
 * Created by jihoon on 11/7/2016.
 */
public class RollCommand extends Command {

    private RollNumberObject rollNumberObject;

    public RollCommand(HttpExchange httpExchange) {
        super(httpExchange);
        rollNumberObject = gson.fromJson(json, RollNumberObject.class);
    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){
            String response = ServerFacade.getInstance().rollNumber(super.gameId,
                                                    rollNumberObject.getNumber());

            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {

                String userName = ServerModel.getInstance().getUsernameFromID(super.playerId);
                String historyMessage = userName + " rolled a " + rollNumberObject.getNumber();
                ServerModel.getInstance().getGame(super.gameId).addLog(historyMessage, userName);

                // Returns the client model JSON (identical to /game/model)
                return new JsonPrimitive(response);
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }
}
