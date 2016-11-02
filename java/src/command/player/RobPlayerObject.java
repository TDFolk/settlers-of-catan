package command.player;

import shared.locations.HexLocation;

/**
 * Created by jihoon on 9/30/2016.
 */
public class RobPlayerObject {
    private final String type = "robPlayer";
    private int playerIndex;
    private int victimIndex;
    private HexLocation location;

    public RobPlayerObject(int playerIndex, int victimIndex, HexLocation location) {
        this.playerIndex = playerIndex;
        this.victimIndex = victimIndex;
        this.location = location;
    }

    public String toJSON(){
        String response = "{\n" +
                "  \"type\": \"robPlayer\",\n" +
                "  \"playerIndex\": " + playerIndex + ",\n" +
                "  \"victimIndex\": " + victimIndex + ",\n" +
                "  \"location\": {\n" +
                "    \"x\": " + location.getX() + ",\n" +
                "    \"y\": " + location.getY() + "\n" +
                "  }\n" +
                "}";
        return response;
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

    public int getVictimIndex() {
        return victimIndex;
    }

    public void setVictimIndex(int victimIndex) {
        this.victimIndex = victimIndex;
    }

    public HexLocation getLocation() {
        return location;
    }

    public void setLocation(HexLocation location) {
        this.location = location;
    }
}
