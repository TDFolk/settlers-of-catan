package decoder.JsonModels;

import shared.locations.HexLocation;
import shared.locations.VertexDirection;

/**
 * Created by bvance on 9/30/2016.
 */
public class JsonPiece {

    private String resource;
    private int ratio;
    private int owner = -1;
    private String direction;
    private JsonLocation location;
    private HexLocation hexLocation;
    private VertexDirection vertexDirection;

    public JsonPiece(){};

    public JsonPiece(String resource, int ratio, String direction, JsonLocation location)
    {
        this.resource = resource;
        this.ratio = ratio;
        this.direction = direction;
        this.location = location;
    }

    public JsonPiece(String direction, JsonLocation location, HexLocation hexLocation, VertexDirection vertexDirection) {
        this.direction = direction;
        this.location = location;
        this.hexLocation = hexLocation;
        this.vertexDirection = vertexDirection;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public JsonLocation getLocation() {
        return location;
    }

    public void setLocation(JsonLocation location) {
        this.location = location;
    }

    public HexLocation getHexLocation() {
        return hexLocation;
    }

    public VertexDirection getVertexDirection() {
        return vertexDirection;
    }
}
