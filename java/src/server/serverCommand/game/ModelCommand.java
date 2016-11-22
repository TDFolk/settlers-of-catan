package server.serverCommand.game;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;

/**
 * Created by jihoon on 11/7/2016.
 */
public class ModelCommand extends Command {
	
	private int versionNumber = 0;

    public ModelCommand(HttpExchange httpExchange) {
        super(httpExchange);

        String url = exchange.getRequestURI().toString();
        String[] split = url.split("/");
        String model = split[split.length - 1];
        String[] split2 = model.split("=");
        versionNumber = Integer.parseInt(split2[split2.length -1]);
    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        String response = ServerFacade.getInstance().gameModel(versionNumber, super.gameId);
        if(response == null){
            return new JsonPrimitive("Invalid");
        }
        else {

            return new JsonPrimitive(gson.toJson(response, ServerGameModel.class));
        }
    }
}
