package server.serverCommand.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.user.LoginObject;
import server.ServerFacade;
import server.ServerProxy;
import server.serverCommand.Command;
import server.serverModel.ServerModelFacade;

/**
 * Created by jihoon on 11/7/2016.
 */
public class LoginCommand extends Command {

//    private String username;
//    private String password;
    private LoginObject loginObject;

    public LoginCommand(HttpExchange httpExchange) throws IOException {
        super(httpExchange);
        loginObject = gson.fromJson(json, LoginObject.class);
        
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
//    	System.out.println("USERNAME: " + username);
//    	System.out.println("PASSWORD: " + password);
    }

    @Override
    public JsonElement execute() throws Exception{

//        boolean response = ServerFacade.getInstance().userLogin(username, password);
        boolean response = ServerFacade.getInstance().userLogin(loginObject.getUsername(), loginObject.getPassword());

        if(response){
            //do something here
            





            return new JsonPrimitive("Success");

        }
        else {
            System.out.println("Internal Server Error");
            throw new Exception();
        }

    }
}
