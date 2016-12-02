package server;

import client.data.GameInfo;
import client.data.PlayerInfo;
import client.join.JoinGameController;
import com.google.gson.JsonObject;
import command.game.GameListHolder;
import model.Facade;
import model.Player;

import java.util.ArrayList;
import java.util.List;
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
    private GameInfo[] oldGameList;
    private GameInfo[] newGameList;
    private IServer proxy = ServerProxy.getServer();

    Timer myTimer = new Timer();
    TimerTask myTimerTask = new TimerTask() {
        @Override
        public void run() {

            System.out.println(TIME_INTERVAL/1000  + " second/s");

            newGameList = proxy.gameList().getGameInfos();

            if( oldGameList == null || gameListHasChanged(oldGameList, newGameList))
            {
                System.out.println("Updating the GameInfo");

                oldGameList = newGameList;

                Facade.getInstance().updateGameInfo(newGameList);
            }

            if(JoinGameController.canPoll())
            {

                currentModel = proxy.gameModelVersion(Facade.getInstance().getVersionNumber());
                if (currentModel != null) {
                    if (!currentModel.equals("\"true\"")) {

                        System.out.println("Updating Model");
                        Facade.getInstance().replaceModel(currentModel);
                        //Facade.getInstance().incrementVersionNumber();
                    }
                }
            }

        }
    };

    private boolean gameListHasChanged(GameInfo[] oldGameList, GameInfo[] newGameList) {

        if(oldGameList.length != newGameList.length)
        {
            return true;
        }
        else {

            for (int i = 0; i < oldGameList.length; i++) {
                if(oldGameList[i].getId() != newGameList[i].getId())
                {
                    return true;
                }

                if(!oldGameList[i].getTitle().equals(newGameList[i].getTitle()))
                {
                    return true;
                }

                List<PlayerInfo> oldPLayers = oldGameList[i].getPlayers();
                List<PlayerInfo> newPlayers = newGameList[i].getPlayers();

                if(oldPLayers.size() != newPlayers.size())
                {
                    return true;
                }

                //check the info of each player to verify it is the same
                for(int j = 0; j < oldPLayers.size(); j++)
                {
                    if(!oldPLayers.get(j).getName().equals(newPlayers.get(j).getName()))
                    {
                        return true;
                    }

                    if (oldPLayers.get(j).getColor() != newPlayers.get(j).getColor())
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

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
