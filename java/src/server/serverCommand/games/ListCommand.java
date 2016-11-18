package server.serverCommand.games;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;
import command.game.GameListHolder;
import server.ServerProxy;
import server.serverCommand.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jihoon on 11/7/2016.
 */
public class ListCommand extends Command {

    public ListCommand(HttpExchange httpExchange) throws IOException{
        super(httpExchange);
    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        GameListHolder gameListHolder = ServerProxy.getServer().gameList();
        return new JsonPrimitive(gson.toJson(gameListHolder));
    }
}
