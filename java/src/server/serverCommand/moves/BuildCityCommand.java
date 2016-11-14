package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.serverCommand.Command;
import shared.locations.VertexLocation;

/**
 * Created by jihoon on 11/7/2016.
 */
public class BuildCityCommand extends Command {
	
	private int playerIndex;
	private VertexLocation vertexLocation;

    public BuildCityCommand(HttpExchange httpExchange, int playerIndex, VertexLocation vertexLocation) {
        super(httpExchange);
        this.playerIndex = playerIndex;
        this.vertexLocation = vertexLocation;
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
