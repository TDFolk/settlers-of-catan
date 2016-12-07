package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.YearOfPlentyObject;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;
import shared.definitions.ResourceType;

/**
 * Created by jihoon on 11/7/2016.
 */
public class YearOfPlentyCommand extends Command {
	
//	private int playerIndex;
//	private ResourceType resource1;
//	private ResourceType resource2;
    private String resource1;
    private String resource2;
    private YearOfPlentyObject yearOfPlentyObject;

    public YearOfPlentyCommand(HttpExchange httpExchange) {
        super(httpExchange);
        json = json.replaceAll("\\n", "");
        yearOfPlentyObject = gson.fromJson(json, YearOfPlentyObject.class);

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        resource1 = jsonObject.get("resource1").getAsString();
        resource2 = jsonObject.get("resource2").getAsString();

    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){

            incrementCards(resource1);
            incrementCards(resource2);

            ServerModel.getInstance().getGame(super.gameId).incrementVersion();
            String response = ServerModel.getInstance().getGame(super.gameId).getJsonFromModel();

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

    private void incrementCards(String resourceType){
        int currentAmount;
        switch(resourceType.toLowerCase()){
            case "brick":
                currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[yearOfPlentyObject.getPlayerIndex()].getResources().getBrick();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[yearOfPlentyObject.getPlayerIndex()].getResources().setBrick(currentAmount + 1);
                break;
            case "sheep":
                currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[yearOfPlentyObject.getPlayerIndex()].getResources().getSheep();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[yearOfPlentyObject.getPlayerIndex()].getResources().setSheep(currentAmount + 1);
                break;
            case "wood":
                currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[yearOfPlentyObject.getPlayerIndex()].getResources().getWood();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[yearOfPlentyObject.getPlayerIndex()].getResources().setWood(currentAmount + 1);
                break;
            case "wheat":
                currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[yearOfPlentyObject.getPlayerIndex()].getResources().getWheat();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[yearOfPlentyObject.getPlayerIndex()].getResources().setWheat(currentAmount + 1);
                break;
            case "ore":
                currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[yearOfPlentyObject.getPlayerIndex()].getResources().getOre();
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[yearOfPlentyObject.getPlayerIndex()].getResources().setOre(currentAmount + 1);
                break;
        }
    }
}
