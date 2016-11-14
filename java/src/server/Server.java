package server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import server.handler.Handlers;
import server.handler.ServerHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by Trent on 11/14/2016.
 */
public class Server {
    private final int DEFAULT_PORT = 8081;
    private int port;
    private String host;
    private HttpServer server;

    public void init() {

        try {
            server = HttpServer.create(new InetSocketAddress(DEFAULT_PORT), 0);
        }
        catch (IOException e) {
            return;
        }

        server.setExecutor(null); //Default executor

        server.createContext("/user", new ServerHandler());
        server.createContext("/game", new ServerHandler());
        server.createContext("/games", new ServerHandler());
        server.createContext("/moves", new ServerHandler());

        server.createContext("/docs/api/data", new Handlers.JSONAppender(""));
        server.createContext("/docs/api/view", new Handlers.BasicFile(""));

        server.start();
    }

    public static void main(String[] args) {
        new Server().init();
    }

}
