package model;

import com.google.gson.JsonObject;

/**
 * Facade class that stands between the Game and all other components. Anything that would access or change anything
 * about the model MUST go through this class.
 *
 * Created by kcwillmore on 9/17/16.
 */
public class Facade {
    private static Facade instance = null;

    private Facade() {
    }

    /**
     * Gets the current instance of the facade
     * @return current instance of the facade
     */
    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    /**
     * Replaces the old model with the new model returned from the server
     * @param newModel the new model returned from the server
     */
    public void replaceModel(JsonObject newModel) {}
}
