package server;

/**
 * Created by jihoon on 9/16/2016.
 */
public class ServerPoller {

    private IServer proxy;
    private final long TIME_INTERVAL = 1000;

    public ServerPoller(IServer proxyServer){

        this.proxy = proxyServer;

    }

}
