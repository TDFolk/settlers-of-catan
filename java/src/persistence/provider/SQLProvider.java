package persistence.provider;

import persistence.dao.*;

/**
 * Created by jihoon on 11/28/2016.
 */
public class SQLProvider implements IProvider {
    ICommandDAO commandDAO;
    IGameDAO gameDAO;
    IUserDAO userDAO;

    public SQLProvider() {
        commandDAO = new SQLCommandDAO();
        gameDAO = new SQLGameDAO();
        userDAO = new SQLUserDAO();
    }

    /**
     * Starts a transaction
     */
    @Override
    public void startTransaction() {

    }

    /**
     * Commits the changes to the database and ends the transaction
     */
    @Override
    public void endTransaction() {

    }

    /**
     * Clears database
     */
    @Override
    public void clearData() {

    }

    /**
     * Gets an instance of the user DAO from plugin
     */
    @Override
    public void getUserDAO() {

    }

    /**
     * Gets an instance of the game DAO from plugin
     */
    @Override
    public void getGameDAO() {

    }

}
