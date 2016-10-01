package command.player;

import com.google.gson.Gson;
import shared.locations.EdgeLocation;


/**
 * Created by jihoon on 9/30/2016.
 */
public class BuildRoadObject {
    private final String type = "buildRoad";
    private int playerIndex;
    private EdgeLocation roadLocation;
    private boolean free;

    public BuildRoadObject(int playerIndex, EdgeLocation roadLocation, boolean free){
        this.playerIndex = playerIndex;
        this.roadLocation = roadLocation;
        this.free = free;
    }

    public String toJSON(){
//        Gson gson = new Gson();
//        return gson.toJson(this);
        String response = "{\n" +
                "  \"type\": \"buildRoad\",\n" +
                "  \"playerIndex\": \"integer\",\n" +
                "  \"roadLocation\": {\n" +
                "    \"x\": \"" + roadLocation.getHexLoc().getX() + "\",\n" +
                "    \"y\": \"" + roadLocation.getHexLoc().getY() + "\",\n" +
                "    \"direction\": \"" + roadLocation.getDir() + "\"\n" +
                "  },\n" +
                "  \"free\": \"Boolean\"\n" +
                "}";
        return response;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public void setRoadLocation(EdgeLocation roadLocation) {
        this.roadLocation = roadLocation;
    }

    public EdgeLocation getRoadLocation() {
        return roadLocation;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public String getType() {

        return type;
    }
}
