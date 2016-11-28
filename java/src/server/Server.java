package server;

import com.sun.net.httpserver.HttpServer;
import persistence.provider.IProvider;
import persistence.provider.SQLProvider;
import server.handler.Handlers;
import server.handler.ServerHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.*;

/**
 * Created by Trent on 11/14/2016.
 */
public class Server {
    private final int DEFAULT_PORT = 8081;
    //private int port;
    //private String host;
    private HttpServer server;
    private Logger logger;

//    private Server(int port){
//        this.port = port;
//    }

    private void initLogger() throws IOException{

        logger = Logger.getLogger("server");
        logger.setLevel(Level.FINE);
        logger.setUseParentHandlers(false);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINE);
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);

        FileHandler fileHandler = new FileHandler("log.txt, false");
        fileHandler.setLevel(Level.FINE);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    public void init() {

        try {
            initLogger();
            logger.info("Initializing Server");
            server = HttpServer.create(new InetSocketAddress(DEFAULT_PORT), 10);
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return;
        }

        server.setExecutor(null); //Default executor

        ServerHandler serverHandler = new ServerHandler();
        server.createContext("/", serverHandler);
        serverHandler.setLogger(logger);

//        server.createContext("/user", new ServerHandler());
//        server.createContext("/game", new ServerHandler());
//        server.createContext("/games", new ServerHandler());
//        server.createContext("/moves", new ServerHandler());

        server.createContext("/docs/api/data", new Handlers.JSONAppender(""));
        server.createContext("/docs/api/view", new Handlers.BasicFile(""));
        // Swagger works when you run our server and go to http://localhost:8081/docs/api/view/index.html


        server.start();
    }


    /**
     * instantiate plugin Persistence Provider object
     * @return
     */
    public IProvider initPlugin() {
        return new SQLProvider();
    }

    public static void main(String[] args) {
        new Server().init();
    }

}
