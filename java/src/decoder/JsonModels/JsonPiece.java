package decoder.JsonModels;

/**
 * Created by bvanc on 9/30/2016.
 */
public class JsonPiece {

    private String resource;
    private int ratio;
    private int owner = -1;
    private String direction;
    private JsonLocation location;

    public JsonPiece(){};

    public JsonPiece(String resource, int ratio, String direction, JsonLocation location)
    {
        this.resource = resource;
        this.ratio = ratio;
        this.direction = direction;
        this.location = location;
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
}
