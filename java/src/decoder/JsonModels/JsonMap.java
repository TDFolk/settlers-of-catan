package decoder.JsonModels;

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
