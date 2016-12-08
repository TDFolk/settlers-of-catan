package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;
import command.player.FinishTurnObject;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;

/**
 * Created by jihoon on 11/7/2016.
 */
public class FinishTurnCommand extends Command {
	
	private int playerIndex;
    private FinishTurnObject finishTurnObject;

    public FinishTurnCommand(HttpExchange httpExchange) {
        super(httpExchange);
        finishTurnObject = gson.fromJson(json, FinishTurnObject.class);
    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){

            for(int i = 0; i < 4; i++){
                if(ServerModel.getInstance().getGame(super.gameId).getPlayers()[i].getVictoryPoints() >= 10){
                    ServerModel.getInstance().getGame(super.gameId).setWinner(i);
                }
            }

            String response = ServerFacade.getInstance().finishTurn(super.gameId);

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
