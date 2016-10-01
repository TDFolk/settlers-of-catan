package decoder;

import com.google.gson.JsonObject;
import model.Game;
import model.Message;
import model.Player;
import model.TurnTracker;
import model.cards_resources.Bank;
import model.cards_resources.Trade;
import model.map.Map;

import java.util.ArrayList;

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
     * @param json the JSON object to be parsed in order to populate the model
     */
    public void parseJson(String json){

//        int version = parseVersionNumber();
//        Bank bank = parseBank();
//        ArrayList<Message> chatlist= parseChat();
//        ArrayList<Message> loglist= parseLog();
//        Map map = parseMap();
//        ArrayList<Player> players = parsePlayers();
//        Player user = parseUser();
//        Trade trade = parseActiveTrade(); //null if none is ongoing
//        TurnTracker turntracker = parseTurnTracker();
//        Player winner = parseWinner();

 //       Game.getInstance().initializeData(version, bank, chatlist, loglist, map, players, user, trade, turntracker, winner);
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
