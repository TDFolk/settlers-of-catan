package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.YearOfPlentyObject;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import shared.definitions.ResourceType;

/**
 * Created by jihoon on 11/7/2016.
 */
public class YearOfPlentyCommand extends Command {
	
	private int playerIndex;
	private ResourceType resource1;
	private ResourceType resource2;
    private YearOfPlentyObject yearOfPlentyObject;

    public YearOfPlentyCommand(HttpExchange httpExchange) {
        super(httpExchange);
        yearOfPlentyObject = gson.fromJson(json, YearOfPlentyObject.class);
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
            String response = ServerFacade.getInstance().yearOfPlenty(yearOfPlentyObject.getPlayerIndex(),
                                                    yearOfPlentyObject.getResource1(), yearOfPlentyObject.getResource2());

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
