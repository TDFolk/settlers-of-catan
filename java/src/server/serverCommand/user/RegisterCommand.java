package server.serverCommand.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Excluder;
import com.sun.net.httpserver.HttpExchange;
import server.serverCommand.Command;

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
//            JsonElement = ;
//            JsonObject responseObject = ;


        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }

        //return super.execute(json);
        return null;
    }
}
