package server.serverCommand.user;

import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import server.serverCommand.Command;
import server.serverModel.ServerModelFacade;

/**
 * Created by jihoon on 11/7/2016.
 */
public class RegisterCommand extends Command {

    public RegisterCommand(HttpExchange httpExchange) {
        super(httpExchange);
    }

    @Override
    public JsonElement execute() {

        String requestBody = exchange.getRequestBody().toString();
        System.out.println(requestBody);

        try {
            boolean response = ServerModelFacade.getInstance().userRegister("", "");
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
