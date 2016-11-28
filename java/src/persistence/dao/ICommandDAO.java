package persistence.dao;

import java.util.List;

/**
 * Created by jihoon on 11/28/2016.
 */
public interface ICommandDAO {

    /**
     * load the saved commands
     * @return list of saved commands
     */
    public List<String> loadCommands();

    /**
     * add a new command to save
     * @param command serialize form of command to save
     * @param gameID id of game to save to
     */
    public void addCommand(String command, int gameID);

    /**
     * remove all commands
     * @param gameID id of the game to remove all commands from
     */
    public void removeAll(int gameID);

    /**
     * returns the number of commands saved for a game
     * @param gameID id of game to get the number of saved commands from
     * @return number of saved commands
     */
    public int numberOfCommands(int gameID);
}
