package persistence.dao;

import java.util.List;

/**
 * Created by jihoon on 11/28/2016.
 */
public class SQLUserDAO implements IUserDAO{
    /**
     * loads all users
     *
     * @return list of saved users
     */
    @Override
    public List<String> loadUsers() {
        return null;
    }

    /**
     * saves a new user
     *
     * @param user   string of the user to add
     * @param userID id of the specific user to add
     */
    @Override
    public void addUser(String user, int userID) {

    }
}
