package server.serverModel;

import command.game.GameCreateObjectResult;

import java.util.ArrayList;
import java.util.HashMap;
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

    private List<ServerGameModel> games;
    private Map<String, String> usersNAME_PASS = new HashMap<String, String>();
    private List<String> loggedInUsers = null;

    /**
     * Logs a user into the server if they are already registered
     * @return success of login
     */
    public boolean userLogin(String userName, String passWord)
    {
        if(loggedInUsers == null)
        {
            loggedInUsers = new ArrayList<>();
        }

        //check if user registered
        if(alreadyRegistered(userName))
        {
            //is the password the same?
            if(usersNAME_PASS.get(userName).equals(passWord))
            {
                loggedInUsers.add(userName);
                return true;
            }
            else
            {
                //wrong password
                return false;

            }

        }

        return false;

    }

    /**Registers a user if they are not already registered
     *
     * @param userName
     * @param password
     * @return success of register
     */
    public boolean userRegister(String userName, String password)
    {
        //check if not registered
        if(!alreadyRegistered(userName))
        {
            //if not registered, add to list
            usersNAME_PASS.put(userName, password);
            return true;
        }

        return false;
    }

    public GameCreateObjectResult gameCreate(boolean randomTiles,
                                             boolean randomNumbers,
                                             boolean randomPorts,
                                             String gameName) {

        return null;

    }

    /**
     * Lists the possible AI to add to each game
     * @return json string of each AI
     */
    public String listAI(){return listAIJson();}

    /**
     * Creates a new game on the server
     * @return success of creation
     */
    public boolean createGame(){

        
        return false;

    }

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


    private boolean alreadyRegistered(String name)
    {

        if(usersNAME_PASS.containsKey(name))
        {
            return true;
        }

        return false;

    }

    public String gameModel(int gameID, int versionNumber)
    {
        for(ServerGameModel game : games)
        {
            if(game.getGameID() == gameID)
            {
                if(game.getVersion() != versionNumber)
                {
                    return game.gameModel();
                }
                else
                {
                    return "true";
                }
            }
        }

        String errorMessage = "ERROR. ServerModel.gameModel() No model with ID " + gameID + " found.";
        System.out.println(errorMessage);
        return errorMessage;
    }


}
