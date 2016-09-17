package model;

import com.google.gson.JsonObject;

/**
 * Facade class that stands between the Model and all other components. Anything that would access or change anything
 * about the model MUST go through this class.
 *
 * Created by kcwillmore on 9/17/16.
 */
public class Facade {
    private static Facade instance = null;

    protected Facade() {
    }

    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    public void replaceModel(JsonObject newModel) {}
}
