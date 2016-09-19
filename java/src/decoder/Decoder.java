package decoder;

import com.google.gson.JsonObject;
import model.Model;

/**
 * This class parses the JSON data and also turns objects into JSON objects to send back to the server
 * Created by jihoon on 9/19/2016.
 */
public class Decoder {

    public static final Decoder SINGLETON = new Decoder();

    private Decoder(){

    }

    /**
     * This function parses the JSON sent from the server to populate the model class
     * @return new model to be written to the database
     */
    public Model parseJson(){
        return null;
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
