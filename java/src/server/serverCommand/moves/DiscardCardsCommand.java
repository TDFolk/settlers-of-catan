package server.serverCommand.moves;

import com.google.gson.JsonPrimitive;
import command.player.DiscardCardsObject;
import model.cards_resources.ResourceCards;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;

/**
 * Created by jihoon on 11/7/2016.
 */
public class DiscardCardsCommand extends Command {
	
	private int playerIndex;
	private ResourceCards discardedCards;
    private DiscardCardsObject discardCardsObject;

    public DiscardCardsCommand(HttpExchange httpExchange) {
        super(httpExchange);
        discardCardsObject = gson.fromJson(json, DiscardCardsObject.class);
    }

    /**
     * This method will handle executing the commands
     *
     * @param json jsonString that will populate the JsonElement
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){
            String response = ServerFacade.getInstance().discardCards(discardCardsObject.getPlayerIndex(),
                                                        discardCardsObject.getDiscardedCards());

            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {
                // Returns the client model JSON (identical to /game/model)
                return new JsonPrimitive(gson.toJson(response, ServerGameModel.class));
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }
}
