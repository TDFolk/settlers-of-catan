package server;

/**The poller is responsible for continuously polling, or asking, the server if the current client model is up to date
 * or not. Triggers an update on the client side when the version of the current client model
 * version number does not match that of the server model
 * Created by //Brandon on 9/16/2016.
 */
public class ServerPoller {

    private IServer proxy;
    private final long TIME_INTERVAL = 1000;

    /**
     *
     * @param proxyServer A reference to the proxy or mock server interface which the poller will go through
     *                    to check
     */
    public ServerPoller(IServer proxyServer){

        this.proxy = proxyServer;

    }

    public void startPoller(){}

    public void stopPoller(){}

}
