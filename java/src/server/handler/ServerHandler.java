package server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by jihoon on 11/7/2016.
 */
public class ServerHandler implements HttpHandler {

    //protected String root;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }

    /*
    public Handlers(String root){
        this.root = root;
    }
    */

    public String getRequestPath(HttpExchange exchange){
        return exchange.getRequestURI().getPath().substring(1);
    }

    public void sendFile(HttpExchange exchange, String filePath){

    }

    public void appendJson(){

    }

    public String getLoginCookie(){

        return null;
    }

    public String getJoinGameCookie(){

        return null;
    }


}
