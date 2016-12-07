package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;
import command.player.BuyDevCardObject;
import server.Server;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;

import java.util.Random;

/**
 * Created by jihoon on 11/7/2016.
 */
public class BuyDevCardCommand extends Command {
	
//	private int playerIndex;
    private BuyDevCardObject buyDevCardObject;

    public BuyDevCardCommand(HttpExchange httpExchange) {
        super(httpExchange);
        buyDevCardObject = gson.fromJson(json, BuyDevCardObject.class);
    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){


//            String response = ServerFacade.getInstance().buyDevCard(buyDevCardObject.getPlayerIndex());

            buyDevCard(buyDevCardObject.getPlayerIndex());

            String response = ServerModel.getInstance().getGame(super.gameId).getJsonFromModel();

            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {
                // Returns the client model JSON (identical to /game/model)

                ServerModel.getInstance().getGame(super.gameId).incrementVersion();
                return new JsonPrimitive(response);
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }

    private void buyDevCard(int playerIndex){
        if(ServerModel.getInstance().getGame(super.gameId).getDeck().leftInDeck() > 0){
            //get a random number...

            Random rand = new Random();
            int r = rand.nextInt(100);

            //give them a
            if(r >= 98 && ServerModel.getInstance().getGame(super.gameId).getDeck().getMonopoly() != 0){
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[buyDevCardObject.getPlayerIndex()].getNewDevCards().incrementMonopoly();

                ServerModel.getInstance().getGame(super.gameId).getDeck().decrementMonopoly();
            }
            else if(r >= 96 && r < 98 && ServerModel.getInstance().getGame(super.gameId).getDeck().getYearOfPlenty() != 0) {
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[buyDevCardObject.getPlayerIndex()].getNewDevCards().incrementYearOfPlenty();

                ServerModel.getInstance().getGame(super.gameId).getDeck().decrementYearOfPlenty();
            }
            else if(r >= 93 && r < 96 && ServerModel.getInstance().getGame(super.gameId).getDeck().getRoadBuilding() != 0){
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[buyDevCardObject.getPlayerIndex()].getNewDevCards().incrementRoadBuilding();

                ServerModel.getInstance().getGame(super.gameId).getDeck().decrementRoadBuilding();
            }
            else if(r >= 40 && r < 93 && ServerModel.getInstance().getGame(super.gameId).getDeck().getSoldier() != 0){
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[buyDevCardObject.getPlayerIndex()].getNewDevCards().incrementSoldier();

                ServerModel.getInstance().getGame(super.gameId).getDeck().decrementSoldier();
            }
            else if(r < 40 && ServerModel.getInstance().getGame(super.gameId).getDeck().getMonument() != 0){
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[buyDevCardObject.getPlayerIndex()].getNewDevCards().incrementMonument();

                ServerModel.getInstance().getGame(super.gameId).getDeck().decrementMonument();
            }
            else {
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[buyDevCardObject.getPlayerIndex()].getNewDevCards().incrementSoldier();

                ServerModel.getInstance().getGame(super.gameId).getDeck().decrementSoldier();
            }
        }
    }
}
