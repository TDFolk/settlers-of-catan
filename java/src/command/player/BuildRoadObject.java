package command.player;

import com.google.gson.Gson;


/**
 * Created by jihoon on 9/30/2016.
 */
public class BuildRoadObject {
    private final String type = "buildRoad";
    private int playerIndex;
    private RoadLocation roadLocation;
    private boolean free;

    public BuildRoadObject(int playerIndex, RoadLocation roadLocation, boolean free){
        this.playerIndex = playerIndex;
        this.roadLocation = roadLocation;
        this.free = free;
    }

    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
//        String jsonString = "{\n" +
//                "  \"type\": \"buildRoad\",\n" +
//                "  \"playerIndex\": \"integer\",\n" +
//                "  \"roadLocation\": {\n" +
//                "    \"x\": \"integer\",\n" +
//                "    \"y\": \"integer\",\n" +
//                "    \"direction\": \"string\"\n" +
//                "  },\n" +
//                "  \"free\": \"Boolean\"\n" +
//                "}";
//        return jsonString;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public RoadLocation getRoadLocation() {
        return roadLocation;
    }

    public void setRoadLocation(RoadLocation roadLocation) {
        this.roadLocation = roadLocation;
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
