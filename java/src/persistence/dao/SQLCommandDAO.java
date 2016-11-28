package persistence.dao;

import java.util.List;

/**
 * Created by jihoon on 11/28/2016.
 */
public class SQLCommandDAO implements ICommandDAO{
    /**
     * load the saved commands
     *
     * @return list of saved commands
     */
    @Override
    public List<String> loadCommands() {
        return null;
    }

    /**
     * add a new command to save
     *
     * @param command name of command to save
     * @param gameID  id of game to save to
     */
    @Override
    public void addCommand(String command, int gameID) {

    }

    /**
     * remove all commands
     *
     * @param gameID id of the game to remove all commands from
     */
    @Override
    public void removeAll(int gameID) {

    }

    /**
     * returns the number of commands saved for a game
     *
     * @param gameID id of game to get the number of saved commands from
     * @return number of saved commands
     */
    @Override
    public int numberOfCommands(int gameID) {
        return 0;
    }
}
