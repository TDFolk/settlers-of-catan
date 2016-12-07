package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;
import command.player.MonumentObject;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;

/**
 * Created by jihoon on 11/7/2016.
 */
public class MonumentCommand extends Command {
	
	private int playerIndex;
    private MonumentObject monumentObject;

    public MonumentCommand(HttpExchange httpExchange) {
        super(httpExchange);
        monumentObject = gson.fromJson(json, MonumentObject.class);
    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){

            int victoryPoints = ServerModel.getInstance().getGame(super.gameId).getPlayers()[monumentObject.getPlayerIndex()].getVictoryPoints();
            int monuments = ServerModel.getInstance().getGame(super.gameId).getPlayers()[monumentObject.getPlayerIndex()].getMonuments();

            if(victoryPoints + monuments >= 10){
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[monumentObject.getPlayerIndex()].setVictoryPoints(10);
            }

            ServerModel.getInstance().getGame(super.gameId).incrementVersion();
            String response = ServerModel.getInstance().getGame(super.gameId).getJsonFromModel();

            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {
                return new JsonPrimitive(response);
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }

}
