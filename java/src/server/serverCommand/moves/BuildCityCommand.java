package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import server.serverCommand.Command;

/**
 * Created by jihoon on 11/7/2016.
 */
public class BuildCityCommand extends Command {

    public BuildCityCommand(HttpExchange httpExchange) {
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
