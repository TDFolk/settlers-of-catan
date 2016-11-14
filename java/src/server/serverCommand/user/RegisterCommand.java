package server.serverCommand.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Excluder;
import com.sun.net.httpserver.HttpExchange;
import server.serverCommand.Command;
import server.serverModel.ServerModelFacade;

/**
 * Created by jihoon on 11/7/2016.
 */
public class RegisterCommand extends Command {
    private String username;
    private String password;

    public RegisterCommand(HttpExchange httpExchange, String username, String password) {
        super(httpExchange);
        this.username = username;
        this.password = password;
    }

    @Override
    public JsonElement execute() {
        try {
            boolean response = ServerModelFacade.getInstance().userRegister(username, password);
            if(response){
                //do something here
                if(exchange == null){
                    //do something else
                }


            }
            else {
                System.out.println("Internal Server Error");
                throw new Exception();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
