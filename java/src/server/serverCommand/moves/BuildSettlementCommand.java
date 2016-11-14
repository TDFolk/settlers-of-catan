package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.serverCommand.Command;
import shared.locations.VertexLocation;

/**
 * Created by jihoon on 11/7/2016.
 */
public class BuildSettlementCommand extends Command {
	
	private int playerIndex;
	private VertexLocation vertexLocation;
	private boolean free;

    public BuildSettlementCommand(HttpExchange httpExchange, int playerIndex, VertexLocation vertexLocation, boolean free) {
        super(httpExchange);
        this.playerIndex = playerIndex;
        this.vertexLocation = vertexLocation;
        this.free = free;
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
