package server.serverCommand.games;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.game.GameCreateObject;
import command.game.GameCreateObjectResult;
import server.ServerFacade;
import server.serverCommand.Command;

/**
 * Created by jihoon on 11/7/2016.
 */
public class CreateCommand extends Command {

    private GameCreateObject gameCreateObject;

    public CreateCommand(HttpExchange httpExchange) {
        super(httpExchange);
        gameCreateObject = gson.fromJson(json, GameCreateObject.class);

    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() throws Exception{
        GameCreateObjectResult gameCreateObjectResult = ServerFacade.getInstance().gameCreate(gameCreateObject.isRandomTiles(), gameCreateObject.isRandomNumbers(), gameCreateObject.isRandomPorts(), gameCreateObject.getName());
        if(hasUserCookie){
            return new JsonPrimitive(gson.toJson(gameCreateObjectResult));
        }
        else {
            System.out.println("Need a user cookie before calling this command");
            throw new Exception();
        }
    }
}
