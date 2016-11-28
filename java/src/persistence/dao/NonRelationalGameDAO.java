package persistence.dao;

import java.util.List;

/**
 * Created by jihoon on 11/28/2016.
 */
public class NonRelationalGameDAO implements IGameDAO{
    /**
     * loads all games that are saved
     *
     * @return all of the games saved
     */
    @Override
    public List<String> loadGames() {
        return null;
    }

    /**
     * update and save a game
     *
     * @param game   string of the game (needs to deserialize)
     * @param gameID id of the specific game
     */
    @Override
    public void updateGame(String game, int gameID) {

    }

    /**
     * saves a new game
     *
     * @param game   string of the game
     * @param gameID id of the specific game
     */
    @Override
    public void addGame(String game, int gameID) {

    }
}
