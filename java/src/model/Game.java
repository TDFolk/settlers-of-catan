package model;

import model.map.Map;

import java.util.List;

/**
 * Game class, contains collections of all model objects within the game
 * Singleton because there can Only Be One
 *
 * Created by kcwillmore on 9/17/16.
 */
public class Game {
    private static Game instance = null;

    private int versionNumber = 0;
    private Bank bank;
    private Map map;
    private List<Player> players;
    /**
     * the player currently in possession of the longest road, begins as null
     */
    private Player longestRoad = null;
    /**
     * the player currently in possession of the largest army, begins as null
     */
    private Player largestArmy = null;

    private Game() {}


    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }
        return instance;
    }

    /**
     * Initialize/reinitialize all of the data in the model from the info returned by the server
     */
    public void initializeData() {

    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void incrementVersionNumber() {
        versionNumber++;
    }

    public Map getMap() {
        return map;
    }
}
