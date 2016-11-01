package command.player;

import client.map.MapController;
import shared.locations.VertexLocation;

/**
 * Created by jihoon on 9/30/2016.
 */
public class BuildCityObject {
    private final String type = "buildCity";
    private int playerIndex;
    private VertexLocation vertexLocation;

    public BuildCityObject(int playerIndex, VertexLocation vertexLocation) {
        this.playerIndex = playerIndex;
        this.vertexLocation = vertexLocation;
    }

    public String toJSON(){
        String response = "{\n" +
                "  \"type\": \"buildCity\",\n" +
                "  \"playerIndex\": " + playerIndex + ",\n" +
                "  \"vertexLocation\": {\n" +
                "    \"x\": " + vertexLocation.getHexLoc().getX() + ",\n" +
                "    \"y\": " + vertexLocation.getHexLoc().getY() + ",\n" +
                "    \"direction\": \"" + MapController.directionToString(vertexLocation.getDir().toString())+  "\"\n" +
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

    public VertexLocation getVertexLocation() {
        return vertexLocation;
    }

    public void setVertexLocation(VertexLocation vertexLocation) {
        this.vertexLocation = vertexLocation;
    }
}
