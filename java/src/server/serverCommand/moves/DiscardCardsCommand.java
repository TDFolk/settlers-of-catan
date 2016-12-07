package server.serverCommand.moves;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import command.player.DiscardCardsObject;
import model.cards_resources.ResourceCards;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;

import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;

/**
 * Created by jihoon on 11/7/2016.
 */
public class DiscardCardsCommand extends Command {
	
//	private int playerIndex;
//	private ResourceCards discardedCards;
    private DiscardCardsObject discardCardsObject;
    private int brick;
    private int wood;
    private int ore;
    private int sheep;
    private int wheat;

    public DiscardCardsCommand(HttpExchange httpExchange) {
        super(httpExchange);
        discardCardsObject = gson.fromJson(json, DiscardCardsObject.class);

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonObject discardedCards = jsonObject.get("discardedCards").getAsJsonObject();
        brick = discardedCards.get("brick").getAsInt();
        wood = discardedCards.get("wood").getAsInt();
        ore = discardedCards.get("ore").getAsInt();
        sheep = discardedCards.get("sheep").getAsInt();
        wheat = discardedCards.get("wheat").getAsInt();
    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){

            for(int i = 0; i < brick; i++){
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[discardCardsObject.getPlayerIndex()].decrementBrick();
            }

            for(int i = 0; i < wood; i++){
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[discardCardsObject.getPlayerIndex()].decrementWood();

            }

            for(int i = 0; i < ore; i++){
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[discardCardsObject.getPlayerIndex()].decrementOre();

            }

            for(int i = 0; i < sheep; i++){
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[discardCardsObject.getPlayerIndex()].decrementSheep();

            }

            for(int i = 0; i < wheat; i++){
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[discardCardsObject.getPlayerIndex()].decrementWheat();

            }

            ServerModel.getInstance().getGame(super.gameId).getTurnTracker().beginRobbingState();
            ServerModel.getInstance().getGame(super.gameId).incrementVersion();

            String response = ServerModel.getInstance().getGame(super.gameId).getJsonFromModel();

            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {
                //start the robbing state after the discarding state

                return new JsonPrimitive(response);
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }
}
