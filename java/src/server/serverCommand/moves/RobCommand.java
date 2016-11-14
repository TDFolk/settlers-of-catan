package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.serverCommand.Command;
import shared.locations.HexLocation;

/**
 * Created by jihoon on 11/7/2016.
 */
public class RobCommand extends Command {
	
	private int playerIndex;
	private int victimIndex;
	private HexLocation location;

    public RobCommand(HttpExchange httpExchange, int playerIndex, int victimIndex, HexLocation location) {
        super(httpExchange);
        this.playerIndex = playerIndex;
        this.victimIndex = victimIndex;
        this.location = location;
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
