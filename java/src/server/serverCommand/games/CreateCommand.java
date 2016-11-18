package server.serverCommand.games;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import command.game.GameCreateObject;
import command.game.GameCreateObjectResult;
import server.serverCommand.Command;

/**
 * Created by jihoon on 11/7/2016.
 */
public class CreateCommand extends Command {
	
	private boolean randomTiles;
	private boolean randomNumbers;
	private boolean randomPorts;
	private String gameName;

    private GameCreateObject gameCreateObject;

    public CreateCommand(HttpExchange httpExchange) {
        super(httpExchange);








    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        gameCreateObject = gson.fromJson(json, GameCreateObject.class);



        return null;
    }
}
