package command.player;

import com.google.gson.Gson;
import shared.definitions.ResourceType;

/**
 * Created by jihoon on 9/30/2016.
 */
public class YearOfPlentyObject {
    private final String type = "Year_of_Plenty";
    private int playerIndex;
    private ResourceType resource1;
    private ResourceType resource2;

    public YearOfPlentyObject(int playerIndex, ResourceType resource1, ResourceType resource2) {
        this.playerIndex = playerIndex;
        this.resource1 = resource1;
        this.resource2 = resource2;
    }

    public String toJSON(){
        return "{\n" +
                "  \"type\": \"" + type + "\",\n" +
                "  \"playerIndex\": " + playerIndex + ",\n" +
                "  \"resource1\": \"" + resource1.toString().toLowerCase() + "\",\n" +
                "  \"resource2\": \"" + resource2.toString().toLowerCase() + "\"\n" +
                "}";
    }

    public String getType() {
        return type;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public ResourceType getResource1() {
        return resource1;
    }

    public void setResource1(ResourceType resource1) {
        this.resource1 = resource1;
    }

    public ResourceType getResource2() {
        return resource2;
    }

    public void setResource2(ResourceType resource2) {
        this.resource2 = resource2;
    }
}
