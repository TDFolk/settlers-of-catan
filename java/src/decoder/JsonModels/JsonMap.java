package decoder.JsonModels;

/**
 * Created by bvanc on 9/30/2016.
 */
public class JsonMap {

    private final String SOUTH = "S";
    private final String SOUTH_WEST = "SW";
    private final String SOUTH_EAST = "SE";
    private final String NORTH = "N";
    private final String NORTH_WEST = "NW";
    private final String NORTH_EAST = "NE";

    private JsonHex hexes[];
    private JsonPiece roads[];
    private JsonPiece cities[];
    private JsonPiece settlements[];
    private int radius;
    private JsonPiece ports[];
    private JsonRobber robber;

    public JsonMap()
    {
        //TODO finish this......
        ports = defaultPorts();

        this.radius = 3;

        this.robber = new JsonRobber(0, -2);
    }

    //
    private JsonPiece[] defaultPorts()
    {
        int NUMBER_OF_PORTS = 9;
        JsonPiece[] ports = new JsonPiece[NUMBER_OF_PORTS];

        JsonLocation p0 = new JsonLocation(1, -3);
        ports[0] = new JsonPiece("ore", 2, SOUTH, p0);

        JsonLocation p1 = new JsonLocation(2, 1);
        ports[1] = new JsonPiece(null, 3, NORTH_WEST, p1);

        JsonLocation p2 = new JsonLocation(-2, 3);
        ports[2] = new JsonPiece("brick", 2, NORTH_EAST, p2);

        JsonLocation p3 = new JsonLocation(0, 3);
        ports[3] = new JsonPiece(null, 3, NORTH, p3);

        JsonLocation p4 = new JsonLocation(-3, 2);
        ports[4] = new JsonPiece("wood", 2, NORTH_EAST, p4);

        JsonLocation p5 = new JsonLocation(-3, 0);
        ports[5] = new JsonPiece(null, 3, SOUTH_EAST, p5);

        JsonLocation p6 = new JsonLocation(3, -1);
        ports[6] = new JsonPiece("sheep", 2, NORTH_WEST, p6);

        JsonLocation p7 = new JsonLocation(-1, -2);
        ports[7] = new JsonPiece("wheat", 2, SOUTH, p7);

        JsonLocation p8 = new JsonLocation(3, -3);
        ports[8] = new JsonPiece(null, 3, SOUTH_WEST, p8);

        return ports;

    }



    public JsonPiece[] getRoads() {
        return roads;
    }

    public void setRoads(JsonPiece[] roads) {
        this.roads = roads;
    }

    public JsonPiece[] getCities() {
        return cities;
    }

    public void setCities(JsonPiece[] cities) {
        this.cities = cities;
    }

    public JsonPiece[] getSettlements() {
        return settlements;
    }

    public void setSettlements(JsonPiece[] settlements) {
        this.settlements = settlements;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public JsonPiece[] getPorts() {
        return ports;
    }

    public void setPorts(JsonPiece[] ports) {
        this.ports = ports;
    }

    public JsonRobber getRobber() {
        return robber;
    }

    public void setRobber(JsonRobber robber) {
        this.robber = robber;
    }

    public JsonHex[] getHexes() {
        return hexes;
    }

    public void setHexes(JsonHex[] hexes) {
        this.hexes = hexes;
    }
}
