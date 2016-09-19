package model;

import java.util.List;

/**
 * Model class, contains collections of all model objects within the game
 * Singleton because there can Only Be One
 *
 * Created by kcwillmore on 9/17/16.
 */
public class Model {
    private static Model instance = null;

    private Bank bank;
    private Map map;
    private List players;
    /**
     * the player currently in possession of the longest road, begins as null
     */
    private Player longestRoad = null;
    /**
     * the player currently in possession of the largest army, begins as null
     */
    private Player largestArmy = null;

    protected Model() {}


    public static Model getInstance() {
        if(instance == null) {
            instance = new Model();
        }
        return instance;
    }

    /**
     * Initialize/reinitialize all of the data in the model from the info returned by the server
     */
    public void initializeData() {

    }
}
