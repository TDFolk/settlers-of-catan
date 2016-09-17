package server;

/**The poller is responsible for continuously polling, or asking, the server if the current client model is up to date
 * or not. Triggers an update on the client side when the version of the current client model
 * version number does not match that of the server model
 * Created by //Brandon on 9/16/2016.
 */
public class ServerPoller {

    /**
     * @proxy reference to the server proxy that the poller will use to poll the server
     * @TIME_INTERVAL time interval that will be used to poll the server
     */
    private IServer proxy;
    private final long TIME_INTERVAL = 1000;

    /**
     *
     * @param proxyServer A reference to the proxy or mock server interface which the poller will go through
     *                    to check the version of the server model
     */
    public ServerPoller(IServer proxyServer){

        this.proxy = proxyServer;

    }

    /**Begins the constant polling cylce of the server proxy
     * @pre non-null proxy reference
     * @post will poll the proxy once every TIME_INTERVAL amount of time
     */
    public void startPoller(){}

    /**Ends the polling cycle
     * @pre none
     * @post none
     */
    public void stopPoller(){}

}
