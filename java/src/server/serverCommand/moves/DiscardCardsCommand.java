package server.serverCommand.moves;

import model.cards_resources.ResourceCards;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.serverCommand.Command;

/**
 * Created by jihoon on 11/7/2016.
 */
public class DiscardCardsCommand extends Command {
	
	private int playerIndex;
	private ResourceCards discardedCards;

    public DiscardCardsCommand(HttpExchange httpExchange, int playerIndex, ResourceCards discardedCards) {
        super(httpExchange);
        this.playerIndex = playerIndex;
        this.discardedCards = discardedCards;
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
