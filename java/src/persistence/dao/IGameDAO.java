package persistence.dao;

import java.util.List;

/**
 * Created by jihoon on 11/28/2016.
 */
public interface IGameDAO {

    /**
     * loads all games that are saved
     * @return all of the games saved
     */
    public List<String> loadGames();

    /**
     * update and save a game
     * @param game string of the game (needs to deserialize)
     * @param gameID id of the specific game
     */
    public void updateGame(String game, int gameID);

    /**
     * saves a new game
     * @param game string of the game
     * @param gameID id of the specific game
     */
    public void addGame(String game, int gameID);
}
