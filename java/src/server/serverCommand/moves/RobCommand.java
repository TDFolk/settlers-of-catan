package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.RobPlayerObject;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;
import server.serverModel.ServerModelFacade;
import shared.locations.HexLocation;

import java.util.List;
import java.util.Random;

/**
 * Created by jihoon on 11/7/2016.
 */
public class RobCommand extends Command {
	
//	private int playerIndex;
//	private int victimIndex;
	private HexLocation location;

    private RobPlayerObject robPlayerObject;

    public RobCommand(HttpExchange httpExchange) {
        super(httpExchange);
        json = json.replaceAll("\\n", "");
        robPlayerObject = gson.fromJson(json, RobPlayerObject.class);

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        location = fetchHexLocation(jsonObject.get("location").getAsJsonObject());
    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){

            //move the robber location
            ServerModel.getInstance().getGame(super.gameId).getMap().changeRobberLocation(location);

            //steal the victim's resource
            stealFromPlayer(robPlayerObject.getPlayerIndex(), robPlayerObject.getVictimIndex());





            ServerModel.getInstance().getGame(super.gameId).getTurnTracker().beginPlayingState();
            ServerModel.getInstance().getGame(super.gameId).incrementVersion();

            //return the model
            String response = ServerModel.getInstance().getGame(super.gameId).getJsonFromModel();

            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {
                return new JsonPrimitive(response);
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }

    private void stealFromPlayer(int playerIndex, int victimIndex){
        List<String> robList = ServerModel.getInstance().getGame(super.gameId).getPlayers()[victimIndex].getRobList();

        if(robList.size() != 0){
            Random rand = new Random();
            int r = rand.nextInt(robList.size());
            String resource = robList.get(r);
            int currentAmount;
            int victimAmount;
            switch(resource){
                case "brick":
                    victimAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[victimIndex].getResources().getBrick();
                    ServerModel.getInstance().getGame(super.gameId).getPlayers()[victimIndex].getResources().setBrick(victimAmount - 1);

                    currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().getBrick();
                    ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().setBrick(currentAmount + 1);
                    break;
                case "wood":
                    victimAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[victimIndex].getResources().getWood();
                    ServerModel.getInstance().getGame(super.gameId).getPlayers()[victimIndex].getResources().setWood(victimAmount - 1);

                    currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().getWood();
                    ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().setWood(currentAmount + 1);
                    break;
                case "sheep":
                    victimAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[victimIndex].getResources().getSheep();
                    ServerModel.getInstance().getGame(super.gameId).getPlayers()[victimIndex].getResources().setSheep(victimAmount - 1);

                    currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().getSheep();
                    ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().setSheep(currentAmount + 1);
                    break;
                case "wheat":
                    victimAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[victimIndex].getResources().getWheat();
                    ServerModel.getInstance().getGame(super.gameId).getPlayers()[victimIndex].getResources().setWheat(victimAmount - 1);

                    currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().getWheat();
                    ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().setWheat(currentAmount + 1);
                    break;
                case "ore":
                    victimAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[victimIndex].getResources().getOre();
                    ServerModel.getInstance().getGame(super.gameId).getPlayers()[victimIndex].getResources().setOre(victimAmount - 1);

                    currentAmount = ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().getOre();
                    ServerModel.getInstance().getGame(super.gameId).getPlayers()[playerIndex].getResources().setOre(currentAmount + 1);
                    break;
            }
        }
    }
}
