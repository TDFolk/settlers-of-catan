package server.serverCommand.game;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.game.GameAddAIObject;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;

/**
 * Created by jihoon on 11/7/2016.
 */
public class AddAICommand extends Command {
	
	private String AIType;
    private GameAddAIObject gameAddAIObject;

    public AddAICommand(HttpExchange httpExchange) {
        super(httpExchange);
        gameAddAIObject = gson.fromJson(json, GameAddAIObject.class);
    }

    /**
     * This method will handle executing the commands
     *
     * @param json jsonString that will populate the JsonElement
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        //ServerGameModel game = ServerModel.getInstance().getGame(gameId);
        //game.addAI();
        //game.incrementVersion();
        return new JsonPrimitive("Success");
    }
}
