package server.serverCommand.game;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import server.serverCommand.Command;

/**
 * Created by jihoon on 11/7/2016.
 */
public class ModelCommand extends Command {
	
	private int versionNumber;

    public ModelCommand(HttpExchange httpExchange, int versionNumber) {
        super(httpExchange);
        this.versionNumber = versionNumber;
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
