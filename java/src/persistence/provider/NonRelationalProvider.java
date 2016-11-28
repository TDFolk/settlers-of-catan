package persistence.provider;

import persistence.dao.*;

/**
 * Created by jihoon on 11/28/2016.
 */
public class NonRelationalProvider implements IProvider {
    ICommandDAO commandDAO;
    IGameDAO gameDAO;
    IUserDAO userDAO;

    NonRelationalProvider() {
        commandDAO = new NonRelationalCommandDAO();
        gameDAO = new NonRelationalGameDAO();
        userDAO = new NonRelationalUserDAO();
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
