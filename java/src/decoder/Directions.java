package decoder;

/**
 * Created by bvanc on 11/18/2016.
 */
public enum Directions {

    SOUTH, SOUTH_WEST, SOUTH_EAST, NORTH, NORTH_EAST, NORTH_WEST;

    private String direction;

    static
    {
        SOUTH.direction = "S";
        SOUTH_WEST.direction = "SW";
        SOUTH_EAST.direction = "SE";
        NORTH.direction = "N";
        NORTH_WEST.direction = "NW";
        NORTH_EAST.direction = "NE";
    }

    public String getDirection()
    {
        return direction;
    }

}
