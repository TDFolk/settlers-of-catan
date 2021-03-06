package server.serverCommand.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.user.RegisterObject;
import server.ServerFacade;
import server.ServerProxy;
import server.serverCommand.Command;
import server.serverModel.ServerModelFacade;

/**
 * Created by jihoon on 11/7/2016.
 */
public class RegisterCommand extends Command {

	private RegisterObject registerObject;
	private String username;
	private String password;

    public RegisterCommand(HttpExchange httpExchange) throws IOException {
        super(httpExchange);
      	registerObject = gson.fromJson(json, RegisterObject.class);

		username = registerObject.getUsername();
		password = registerObject.getPassword();


//        InputStreamReader isr =  new InputStreamReader(httpExchange.getRequestBody(),"utf-8");
//    	BufferedReader br = new BufferedReader(isr);
//
//    	// From now on, the right way of moving from bytes to utf-8 characters:
//
//    	int b;
//    	StringBuilder buf = new StringBuilder();
//    	while ((b = br.read()) != -1) {
//    	    buf.append((char) b);
//    	}
//
//    	br.close();
//    	isr.close();
//
//    	String[] args = buf.toString().split(",");
//    	username = args[0].split(":")[1].substring(1);
//    	username = username.substring(0, username.indexOf("\""));
//    	password = args[1].split(":")[1].substring(1);
//    	password = password.substring(0, password.indexOf("\""));
//
    	System.out.println("USERNAME: " + username);
    	System.out.println("PASSWORD: " + password);
    }

    @Override
    public JsonElement execute() throws Exception{

            boolean response = ServerFacade.getInstance().userRegister(username, password);
            if(response){
                //do something here
				int id = ServerFacade.getInstance().getUserID(username);
                String encodedCookie = getLoginCookie(username, password, Integer.toString(id));
                exchange.getResponseHeaders().add("Set-cookie", encodedCookie);
				super.hasUserCookie = true;
                return new JsonPrimitive("Success");
            }
            else {
                System.out.println("Internal Server Error");
                throw new Exception();
            }

    }
}
