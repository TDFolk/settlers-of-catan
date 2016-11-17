package server.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.serverCommand.Command;
import server.serverCommand.CommandFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jihoon on 11/7/2016.
 */
public class ServerHandler implements HttpHandler {

    private CommandFactory factory;
    private Logger logger = null;

    //constructor
    public ServerHandler(){

    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Command command = factory.create(httpExchange);

        String uri = httpExchange.getRequestURI().toString();
        String[] arguments = uri.split("/");
        String request = arguments[arguments.length - 1];

        //make sure logger is not null
        if(logger != null){
            if(!request.startsWith("model")){
                logger.info("Request: " + httpExchange.getRequestURI().toString());
            }
            try{
                if(command == null){
                    throw new Exception("Invalid Request");
                }
                JsonElement response = command.execute();
                if(response.getClass() == JsonPrimitive.class) {
                    httpExchange.getResponseHeaders().add("Content-Type", "application/text");
                }
                else {
                    httpExchange.getResponseHeaders().add("Content-Type", "application/text");
                }
                String foo = response.toString();
                if(foo.equals("\"Success\"")) {
                    foo = "Success";
                }
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, foo.length());
                httpExchange.getResponseBody().write(foo.getBytes());
                httpExchange.getResponseBody().close();
                httpExchange.close();

            }
            catch(Exception e) {
                e.printStackTrace();
                String error = e.getMessage();
                logger.log(Level.SEVERE, "Bad Request: " + error);
                httpExchange.getResponseHeaders().add("Content-Type", "application/text");
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                httpExchange.getResponseBody().write(error.getBytes());
                httpExchange.getResponseBody().close();
                httpExchange.close();
            }
        }
        else {
            System.out.println("Logger is not initialized...");
        }
    }

    public void setLogger(Logger logger){
        this.logger = logger;
    }
    
}
