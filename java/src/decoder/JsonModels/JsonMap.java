package decoder.JsonModels;

import decoder.Directions;


/**
 * Created by bvanc on 9/30/2016.
 */
public class JsonMap {

    private JsonHex hexes[];
    private JsonPiece roads[];
    private JsonPiece cities[];
    private JsonPiece settlements[];
    private int radius;
    private JsonPiece ports[];
    private JsonRobber robber;

    public JsonPiece[] addToArray( JsonPiece[] arrayToIncrement, JsonPiece newPiece, int playerIndex)
    {
        newPiece.setOwner(playerIndex);
        int newSize = arrayToIncrement.length + 1;
        JsonPiece[] newArray = new JsonPiece[newSize];

        for (int i = 0; i < arrayToIncrement.length; i++)
        {
            newArray[i] = arrayToIncrement[i];
        }

        newArray[newSize - 1] = newPiece;

        return newArray;

    }

    public JsonMap()
    {

        hexes = defaultHexes();
        roads = new JsonPiece[0];
        cities = new JsonPiece[0];
        settlements = new JsonPiece[0];
        ports = defaultPorts();

        this.radius = 3;

        this.robber = new JsonRobber(0, -2);
    }

    private JsonPiece[] defaultPorts()
    {
        int NUMBER_OF_PORTS = 9;
        JsonPiece[] ports = new JsonPiece[NUMBER_OF_PORTS];

        JsonLocation p0 = new JsonLocation(1, -3);
        ports[0] = new JsonPiece("ore", 2, Directions.SOUTH.getDirection(), p0);

        JsonLocation p1 = new JsonLocation(2, 1);
        ports[1] = new JsonPiece(null, 3, Directions.NORTH_WEST.getDirection(), p1);

        JsonLocation p2 = new JsonLocation(-2, 3);
        ports[2] = new JsonPiece("brick", 2, Directions.NORTH_EAST.getDirection(), p2);

        JsonLocation p3 = new JsonLocation(0, 3);
        ports[3] = new JsonPiece(null, 3, Directions.NORTH.getDirection(), p3);

        JsonLocation p4 = new JsonLocation(-3, 2);
        ports[4] = new JsonPiece("wood", 2, Directions.NORTH_EAST.getDirection(), p4);

        JsonLocation p5 = new JsonLocation(-3, 0);
        ports[5] = new JsonPiece(null, 3, Directions.SOUTH_EAST.getDirection(), p5);

        JsonLocation p6 = new JsonLocation(3, -1);
        ports[6] = new JsonPiece("sheep", 2, Directions.NORTH_WEST.getDirection(), p6);

        JsonLocation p7 = new JsonLocation(-1, -2);
        ports[7] = new JsonPiece("wheat", 2, Directions.SOUTH.getDirection(), p7);

        JsonLocation p8 = new JsonLocation(3, -3);
        ports[8] = new JsonPiece(null, 3, Directions.SOUTH_WEST.getDirection(), p8);

        return ports;

    }

    private JsonHex[] defaultHexes()
    {

        int NUMBER_OF_HEXES = 19;

        JsonHex[] hexes = new JsonHex[NUMBER_OF_HEXES];

        JsonLocation loc0 = new JsonLocation(0, -2);
        hexes[0] = new JsonHex(loc0);

        JsonLocation loc1 = new JsonLocation(1, -2);
        hexes[1] = new JsonHex("brick", loc1, 4);

        JsonLocation loc2 = new JsonLocation(2, -2);
        hexes[2] = new JsonHex("wood", loc2, 11);

        JsonLocation loc3 = new JsonLocation(-1, -1);
        hexes[3] = new JsonHex("brick", loc3, 8);

        JsonLocation loc4 = new JsonLocation(0, -1);
        hexes[4] = new JsonHex("wood", loc4, 3);

        JsonLocation loc5 = new JsonLocation(1, -1);
        hexes[5] = new JsonHex("ore", loc5, 9);

        JsonLocation loc6 = new JsonLocation(2, -1);
        hexes[6] = new JsonHex("sheep", loc6, 12);

        JsonLocation loc7 = new JsonLocation(-2, 0);
        hexes[7] = new JsonHex("ore", loc7, 5);

        JsonLocation loc8 = new JsonLocation(-1, 0);
        hexes[8] = new JsonHex("sheep", loc8, 10);

        JsonLocation loc9 = new JsonLocation(0, 0);
        hexes[9] = new JsonHex("wheat", loc9, 11);

        JsonLocation loc10 = new JsonLocation(1, 0);
        hexes[10] = new JsonHex("brick", loc10, 5);

        JsonLocation loc11 = new JsonLocation(2, 0);
        hexes[11] = new JsonHex("wheat", loc11, 6);

        JsonLocation loc12 = new JsonLocation(-2, 1);
        hexes[12] = new JsonHex("wheat", loc12, 2);

        JsonLocation loc13 = new JsonLocation(-1, 1);
        hexes[13] = new JsonHex("sheep", loc13, 9);

        JsonLocation loc14 = new JsonLocation(0, 1);
        hexes[14] = new JsonHex("wood", loc14, 4);

        JsonLocation loc15 = new JsonLocation(1, 1);
        hexes[15] = new JsonHex("sheep", loc15, 10);

        JsonLocation loc16 = new JsonLocation(-2, 2);
        hexes[16] = new JsonHex("wood", loc16 ,6);

        JsonLocation loc17 = new JsonLocation(-1, 2);
        hexes[17] = new JsonHex("ore", loc17 ,3);

        JsonLocation loc18 = new JsonLocation(0, 2);
        hexes[18] = new JsonHex("wheat", loc18 ,8);

        return hexes;
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
