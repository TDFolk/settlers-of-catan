package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import server.serverCommand.Command;

/**
 * Created by jihoon on 11/7/2016.
 */
public class AcceptTradeCommand extends Command {
	
	private int playerIndex;
	private boolean willAccept;

    public AcceptTradeCommand(HttpExchange httpExchange) {
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
        return super.execute();
    }
}
