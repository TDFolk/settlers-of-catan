package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.MonopolyObject;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;

/**
 * Created by jihoon on 11/7/2016.
 */
public class MonopolyCommand extends Command {
	
//	private String resource;
//	private int playerIndex;
    private MonopolyObject monopolyObject;

    public MonopolyCommand(HttpExchange httpExchange) {
        super(httpExchange);
        monopolyObject = gson.fromJson(json, MonopolyObject.class);
    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){

//            String response = ServerFacade.getInstance().monopoly(monopolyObject.getResource(), monopolyObject.getPlayerIndex());
            stealAllResources(monopolyObject.getResource());

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

    private void stealAllResources(String resourceType){
        int addAmount = 0;
        for(int i = 0; i < 4; i++){
            if(i == monopolyObject.getPlayerIndex()){
                continue;
            }
            else{
                addAmount += whichResource(resourceType, i);
            }
        }
        int currentAmount;
        switch(resourceType.toLowerCase()){
            case "brick":
                currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[monopolyObject.getPlayerIndex()].getResources().getBrick();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[monopolyObject.getPlayerIndex()].getResources().setBrick(currentAmount + addAmount);
                break;
            case "wood":
                currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[monopolyObject.getPlayerIndex()].getResources().getWood();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[monopolyObject.getPlayerIndex()].getResources().setWood(currentAmount + addAmount);
                break;
            case "sheep":
                currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[monopolyObject.getPlayerIndex()].getResources().getSheep();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[monopolyObject.getPlayerIndex()].getResources().setSheep(currentAmount + addAmount);
                break;
            case "wheat":
                currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[monopolyObject.getPlayerIndex()].getResources().getWheat();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[monopolyObject.getPlayerIndex()].getResources().setWheat(currentAmount + addAmount);
                break;
            case "ore":
                currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[monopolyObject.getPlayerIndex()].getResources().getOre();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[monopolyObject.getPlayerIndex()].getResources().setOre(currentAmount + addAmount);
                break;
            default:
                currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[monopolyObject.getPlayerIndex()].getResources().getOre();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[monopolyObject.getPlayerIndex()].getResources().setOre(currentAmount + addAmount);
                break;
        }
    }

    private int whichResource(String resourceType, int playerIndex){
        int amount = 0;
        switch(resourceType.toLowerCase()){
            case "brick":
                amount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().getBrick();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().setBrick(0);
                return amount;
            case "wood":
                amount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().getWood();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().setWood(0);
                return amount;
            case "sheep":
                amount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().getSheep();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().setSheep(0);
                return amount;
            case "wheat":
                amount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().getWheat();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().setWheat(0);
                return amount;
            case "ore":
                amount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().getOre();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().setOre(0);
                return amount;
            default:
                amount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().getOre();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().setOre(0);
                return amount;
        }
    }
}
