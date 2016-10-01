package model.pieces;

import exception.PlacementException;
import model.ResourceValues;
import shared.definitions.CatanColor;
import shared.locations.EdgeLocation;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class Road {
    public static final ResourceValues COST = new ResourceValues(1,0,0,0,1);
    private CatanColor color;
    private EdgeLocation location;

    /**
     * Road constructor
     *
     * @param color the color of the player who owns this road
     * @param location the edge location of the road piece, null if unplaced
     */
    public Road(CatanColor color, EdgeLocation location) {
        this.color = color;
        this.location = location;
    }

    public CatanColor getColor() {
        return color;
    }

    public EdgeLocation getLocation() {
        return location;
    }

//    /**
//     * Places a road on the specified EdgeLocation
//     * @pre The road must be placed adjacent to another road or building of the same color,
//     * 				but cannot be placed passing through a settlement of a different color
//     * @post A road is placed at the specified edge location, thus creating a longer road
//     * @param location The edge location to place the road
//     * @throws PlacementException if the road cannot be placed at the specified location
//     */
//    public void placeRoad(EdgeLocation location) throws PlacementException {
//    	this.location = location;
//    }
    //todo ask nick, placement vs can place issue (see settlement)
}
