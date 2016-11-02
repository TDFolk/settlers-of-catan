package command.player;

import client.map.MapController;
import shared.locations.EdgeLocation;

/**
 * Created by jihoon on 9/30/2016.
 */
public class RoadBuildingObject {
    private final String type = "Road_Building";
    private int playerIndex;
    private EdgeLocation spot1;
    private EdgeLocation spot2;

    public RoadBuildingObject(int playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
        this.playerIndex = playerIndex;
        this.spot1 = spot1;
        this.spot2 = spot2;
    }

    public String toJSON(){
        String response = "{\n" +
                "  \"type\": \"Road_Building\",\n" +
                "  \"playerIndex\": " + playerIndex + ",\n" +
                "  \"spot1\": {\n" +
                "    \"x\": " + spot1.getHexLoc().getX() + ",\n" +
                "    \"y\": " + spot1.getHexLoc().getY() +",\n" +
                "    \"direction\": \"" + MapController.directionToString(spot1.getDir().toString()) + "\"\n" +
                "  },\n" +
                "  \"spot2\": {\n" +
                "    \"x\": " + spot2.getHexLoc().getX() +",\n" +
                "    \"y\": " + spot2.getHexLoc().getY() + ",\n" +
                "    \"direction\": \"" + MapController.directionToString(spot2.getDir().toString()) + "\"\n" +
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

    public EdgeLocation getSpot1() {
        return spot1;
    }

    public void setSpot1(EdgeLocation spot1) {
        this.spot1 = spot1;
    }

    public EdgeLocation getSpot2() {
        return spot2;
    }

    public void setSpot2(EdgeLocation spot2) {
        this.spot2 = spot2;
    }
}
