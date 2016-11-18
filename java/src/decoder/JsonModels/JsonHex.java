package decoder.JsonModels;

/**
 * Created by bvanc on 9/30/2016.
 */
public class JsonHex {
    private String resource;
    private JsonLocation location;
    private int number;

    public JsonHex(JsonLocation location)
    {
        this.location = location;
    }

    public JsonHex(String resource, JsonLocation location, int number)
    {
        this.resource = resource;
        this.location = location;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public JsonLocation getLocation() {
        return location;
    }

    public void setLocation(JsonLocation location) {
        this.location = location;
    }
}
