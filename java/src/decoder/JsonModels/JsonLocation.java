package decoder.JsonModels;

/**
 * Created by bvanc on 9/30/2016.
 */
public class JsonLocation {

    private int x;
    private int y;
    private String direction;

    public JsonLocation(int x, int y, String direction)
    {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
