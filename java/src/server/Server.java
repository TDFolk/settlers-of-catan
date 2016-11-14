package server;

import com.sun.net.httpserver.HttpServer;
import server.handler.Handlers;

/**
 * Created by Trent on 11/14/2016.
 */
public class Server {
    private int port;
    private String host;
    private HttpServer server;

    public void init() {

        server.createContext("/docs/api/data", new Handlers.JSONAppender(""));
        server.createContext("/docs/api/view", new Handlers.BasicFile(""));

    }

}
