package server.serverCommand.user;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import server.serverCommand.Command;

/**
 * Created by jihoon on 11/7/2016.
 */
public class LoginCommand extends Command {

    private String username;
    private String password;

    public LoginCommand(HttpExchange httpExchange) {
        super(httpExchange);
    }

    @Override
    public JsonElement execute() {
        return null;
    }
}
