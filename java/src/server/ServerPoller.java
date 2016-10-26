package server;

import com.google.gson.JsonObject;
import model.Facade;

import java.util.Timer;
import java.util.TimerTask;

/**The poller runs on its own thread on a timer (around 1-3 second intervals).
 * On those time intervals it gets all the model info from the server to determine any changes.
 * Triggers an update on the client side when the version of the current client model
 * version number does not match that of the server model
 * Created by //Brandon on 9/16/2016.
 */
public class ServerPoller {

    private static ServerPoller poller = new ServerPoller(ServerProxy.getServer());

    public static ServerPoller getPoller()
    {
        return poller;
    }

    /**
     * @proxy reference to the server proxy that the poller will use to poll the server
     * @TIME_INTERVAL time interval that will be used to poll the server
     */
    private final long TIME_INTERVAL = 1000;
    private String currentModel;
    private IServer proxy = ServerProxy.getServer();

    Timer myTimer = new Timer();
    TimerTask myTimerTask = new TimerTask() {
        @Override
        public void run() {
            System.out.println(TIME_INTERVAL/1000  + " second/s");
            currentModel = proxy.gameModelVersion(Facade.getInstance().getVersionNumber());
            if (currentModel != null) {
                if (!currentModel.equals("\"true\"")) {
                    Facade.getInstance().replaceModel(currentModel);
                    Facade.getInstance().incrementVersionNumber();
                }
            }
        }
    };

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
    public void startPoller(){
        myTimer.scheduleAtFixedRate(myTimerTask,1000,TIME_INTERVAL);
    }

    /**Ends the polling cycle
     * @pre none
     * @post none
     */
    public void stopPoller(){
        myTimer.cancel();
    }

    public String getCurrentModel() {
        return currentModel;
    }

    public void setCurrentModel(String currentModel) {
        this.currentModel = currentModel;
    }

}
