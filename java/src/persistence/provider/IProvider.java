package persistence.provider;

/**
 * Created by jihoon on 11/28/2016.
 */
public interface IProvider {
    /**
     * Starts a transaction
     */
    void startTransaction();

    /**
     * Commits the changes to the database and ends the transaction
     */
    void endTransaction();

    /**
     * Clears database
     */
    void clearData();

    /**
     * Gets an instance of the user DAO
     */
    void getUserDAO();

    /**
     * Gets an instance of the game DAO
     */
    void getGameDAO();
}
