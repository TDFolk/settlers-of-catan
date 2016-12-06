package server.serverCommand.moves;

import com.google.gson.JsonPrimitive;
import command.player.OfferTradeObject;
import model.cards_resources.ResourceCards;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;

/**
 * Created by jihoon on 11/7/2016.
 */
public class OfferTradeCommand extends Command {
	
	private int playerIndex;
	private ResourceCards offer;
	private int receiver;
    private OfferTradeObject offerTradeObject;

    public OfferTradeCommand(HttpExchange httpExchange) {
        super(httpExchange);
        offerTradeObject = gson.fromJson(json, OfferTradeObject.class);
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
            String response = ServerFacade.getInstance().offerTrade(super.gameId, offerTradeObject.getPlayerIndex(),
                                                        offerTradeObject.getOffer(), offerTradeObject.getReceiver());

            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {
                // Returns the client model JSON (identical to /game/model)
                return new JsonPrimitive(response);
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }
}
