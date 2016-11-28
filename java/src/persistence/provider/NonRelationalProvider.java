package persistence.provider;

import persistence.dao.*;

/**
 * Created by jihoon on 11/28/2016.
 */
public class NonRelationalProvider extends IProvider {
    ICommandDAO commandDAO;
    IGameDAO gameDAO;
    IUserDAO userDAO;

    public NonRelationalProvider() {
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
}
