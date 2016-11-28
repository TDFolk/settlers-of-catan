package persistence.provider;

import model.Game;
import persistence.dao.ICommandDAO;
import persistence.dao.IGameDAO;
import persistence.dao.IUserDAO;
import server.serverCommand.Command;

import java.util.List;
import java.util.Map;

/**
 * Created by jihoon on 11/28/2016.
 */
public abstract class IProvider {
    /**
     * Starts a transaction
     */
    public abstract void startTransaction();

    /**
     * Commits the changes to the database and ends the transaction
     */
    public abstract void endTransaction();

    /**
     * Clears database
     */
    public abstract void clearData();

    /**
     * load the games
     * @return list of games
     */
    public List<Game> loadGames(){
       return null;
    }

    /**
     * update the game
     * @param game updates a game
     */
    public void updateGame(Game game){

    }

    /**
     * load the users
     * @return list of a map containing username and password, the position of user in the tree map is the user's ID
     */
    public List<Map<String, String>> loadUsers(){
        return null;
    }

    /**
     * add a new user
     * @param username new username
     * @param password new password
     */
    public void addUser(String username, String password){

    }

    /**
     * load all the commands
     * @return list of saved commands
     */
    public List<Command> loadCommands(){
        return null;
    }

    /**
     * add a command to a game
     * @param command command to be added
     * @param gameID specific id of the game to be added to
     */
    public void addCommand(String command, int gameID){

    }

    public IGameDAO getGameDAO(){
        return null;
    }

    public IUserDAO getUserDAO(){
        return null;
    }

    public ICommandDAO getCommandDAO(){
        return null;
    }
}
