package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.serverCommand.Command;
import shared.definitions.ResourceType;

/**
 * Created by jihoon on 11/7/2016.
 */
public class YearOfPlentyCommand extends Command {
	
	private int playerIndex;
	private ResourceType resource1;
	private ResourceType resource2;

    public YearOfPlentyCommand(HttpExchange httpExchange) {
        super(httpExchange);
    }

    /**
     * This method will handle executing the commands
     *
     * @param json jsonString that will populate the JsonElement
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        return null;
    }
}
