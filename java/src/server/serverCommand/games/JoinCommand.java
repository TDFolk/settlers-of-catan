package server.serverCommand.games;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.game.GameJoinObject;
import server.ServerFacade;
import server.serverCommand.Command;

/**
 * Created by jihoon on 11/7/2016.
 */
public class JoinCommand extends Command {

    private GameJoinObject gameJoinObject;

    public JoinCommand(HttpExchange httpExchange) {
        super(httpExchange);
        gameJoinObject = gson.fromJson(json, GameJoinObject.class);
    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() throws Exception{


        boolean response = ServerFacade.getInstance().gameJoin(gameJoinObject.getId(), gameJoinObject.getColor());
        if(response){
            if(super.hasUserCookie){
                String encodedCookie = getJoinGameCookie(Integer.toString(gameJoinObject.getId()));
                exchange.getResponseHeaders().add("Set-cookie", encodedCookie);
                //exchange.getResponseHeaders().add("Cookie:", "catan.user=%7B&22" + name + "%22%3A%22" + Sam + "%22%2C%22" + password + "%");
                super.gameId = gameJoinObject.getId();
                super.hasGameCookie = true;
                return new JsonPrimitive("Success");

            }
            else {
                throw new Exception();
            }

        }
        else {
            System.out.println("Response was false....");
            throw new Exception();
        }
    }
}
