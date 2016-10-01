package command.player;

import com.google.gson.Gson;
import shared.definitions.ResourceType;

/**
 * Created by jihoon on 9/30/2016.
 */
public class MonopolyObject {
    private final String type = "Monopoly";
    private String resource;
    private int playerIndex;

    public MonopolyObject(String resource, int playerIndex) {
        this.resource = resource;
        this.playerIndex = playerIndex;
    }

    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getType() {
        return type;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
