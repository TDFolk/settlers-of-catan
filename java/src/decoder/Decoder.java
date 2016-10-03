package decoder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import decoder.JsonModels.JsonModel;

/**
 * This class parses the JSON data and also turns objects into JSON objects to send back to the server
 * Created by jihoon on 9/19/2016.
 */
public class Decoder {

    public static Decoder instance = new Decoder();

    private Decoder(){

    }

    public static Decoder getInstance() {
        if (instance == null) {
            instance = new Decoder();
        }
        return instance;
    }

    /**
     * This function parses the JSON sent from the server to populate the model class
     * @param newModelJson the JSON object to be parsed in order to populate the model
     */
    public JsonModel parseJson(String newModelJson){
        Gson gson = new Gson();

        JsonModel newModel = gson.fromJson(newModelJson, JsonModel.class);

        return newModel;
    }


    /**
     * This function takes an existing object and converts it to JSON format to be sent back to the server
     * @param o the object to be sent back to the server as a JSON
     * @return JsonObject for the server
     */
    public JsonObject toJson(Object o){
        return null;
    }
}
