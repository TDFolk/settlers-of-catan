package server.serverModel;

import java.util.List;
import java.util.Map;

/**
 * Created by bvanc on 11/7/2016.
 */
public class ServerModel {

    private static ServerModel instance = null;

    private ServerModel() {
    }

    public static ServerModel getInstance() {

        if(instance == null)
        {
            instance = new ServerModel();
        }

        return instance;
    }

    List<ServerGameModel> games;
    Map<String, String> usersNAME_PASS;


    public boolean userLogin(){return false;}

    public boolean registerUser(String userName, String password){return false;}

    public String listAI(){return listAIJson();}

    public boolean createGame(){return false;}

    public String listGames(){return listGamesJSON();}

    private String listGamesJSON() {

        return "list of games";
    }

    private String listAIJson() {

        return "list of AI";

    }




}
