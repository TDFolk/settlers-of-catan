package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import server.serverCommand.Command;

/**
 * Created by jihoon on 11/7/2016.
 */
public class RollCommand extends Command {
	
	private int playerIndex;
	private int number;

    public RollCommand(HttpExchange httpExchange, int playerIndex, int number) {
        super(httpExchange);
        this.playerIndex = playerIndex;
        this.number = number;
    }

    /**
     * This method will handle executing the commands
     *
     * @param json jsonString that will populate the JsonElement
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        return super.execute();
    }
}
