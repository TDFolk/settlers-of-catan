package server.serverModel;

import java.util.List;
import java.util.Map;

/**
 * Model for all games running on the server as well as all of the registered users
 *
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

    /**
     * Logs a user into the server if they are already registered
     * @return success of login
     */
    public boolean userLogin(){return false;}

    /**Registers a user if they are not already registered
     *
     * @param userName
     * @param password
     * @return success of register
     */
    public boolean registerUser(String userName, String password){return false;}

    /**
     * Lists the possible AI to add to each game
     * @return json string of each AI
     */
    public String listAI(){return listAIJson();}

    /**
     * Creates a new game on the server
     * @return success of creation
     */
    public boolean createGame(){return false;}

    /**
     * Returns a list of all the current games running on the server
     * @return Json string of each game Id, ane of , and list of players within
     */
    public String listGames(){return listGamesJSON();}

    private String listGamesJSON() {

        return "list of games";
    }

    private String listAIJson() {

        return "list of AI";

    }




}
