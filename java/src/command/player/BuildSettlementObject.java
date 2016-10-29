package command.player;

import client.map.MapController;
import com.google.gson.Gson;
import shared.locations.VertexLocation;

/**
 * Created by jihoon on 9/30/2016.
 */
public class BuildSettlementObject {
    private final String type = "buildSettlement";
    private int playerIndex;
    private VertexLocation vertexLocation;
    private boolean free;

    public BuildSettlementObject(int playerIndex, VertexLocation vertexLocation, boolean free) {
        this.playerIndex = playerIndex;
        this.vertexLocation = vertexLocation;
        this.free = free;
    }

    public String toJSON(){
        String response = "{\n" +
                "  \"type\": \"buildSettlement\",\n" +
                "  \"playerIndex\": \"" + playerIndex + "\",\n" +
                "  \"vertexLocation\": {\n" +
                "    \"x\": \"" + vertexLocation.getHexLoc().getX() + "\",\n" +
                "    \"y\": \"" + vertexLocation.getHexLoc().getY() + "\",\n" +
                "    \"direction\": \"" + MapController.directionToString(vertexLocation.getDir().toString()) + "\"\n" +
                "  },\n" +
                "  \"free\": \"" + free + "\"\n" +
                "}";

        return response;
//        Gson gson = new Gson();
//        return gson.toJson(this);
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

    public VertexLocation getVertexLocation() {
        return vertexLocation;
    }

    public void setVertexLocation(VertexLocation vertexLocation) {
        this.vertexLocation = vertexLocation;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
