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

/**
 * Created by jihoon on 11/7/2016.
 */
public class RobCommand extends Command {
	
	private int playerIndex;
	private int victimIndex;
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






            String response = ServerModel.getInstance().getGame(super.gameId).getJsonFromModel();

            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {
                ServerModel.getInstance().getGame(super.gameId).getTurnTracker().beginPlayingState();
                ServerModel.getInstance().getGame(super.gameId).incrementVersion();
                return new JsonPrimitive(response);
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }
}
