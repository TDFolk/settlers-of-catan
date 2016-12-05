package server.serverCommand.moves;

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
	
	private int playerIndex;
	private ResourceCards discardedCards;
    private DiscardCardsObject discardCardsObject;

    public DiscardCardsCommand(HttpExchange httpExchange) {
        super(httpExchange);
        discardCardsObject = gson.fromJson(json, DiscardCardsObject.class);


        //TODO: NEEDS WORK AFTER ROLLING STARTS WORKING
        //check what the json contains.. and check the object as well

        String test = json;
        String test2 = json;
    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){

            //decrement the player resource list with the discarded cards resource



//            String response = ServerFacade.getInstance().discardCards(discardCardsObject.getPlayerIndex(),
//                                                        discardCardsObject.getDiscardedCards());

            String response = ServerModel.getInstance().getGame(super.gameId).getJsonFromModel();


            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {
                //start the robbing state after the discarding state
                ServerModel.getInstance().getGame(super.gameId).getTurnTracker().beginRobbingState();

                ServerModel.getInstance().getGame(super.gameId).incrementVersion();
                return new JsonPrimitive(response);
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }
}
