package model;

import shared.locations.EdgeLocation;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class Road {
    private static final Cost cost = new Cost(1,0,0,0,1);

    private EdgeLocation location;

    /**
     * Places a road on the specified EdgeLocation
     * @pre The road must be placed adjacent to another road or settlement of the same color, 
     * 				but cannot be placed passing through a settlement of a different color
     * @post A road is placed at the specified edge location, thus creating a longer road
     * @param location The edge location to place the road
     */
    public void placeRoad(EdgeLocation location) {
    	
    }
    
    /**
     * Determines if a road can be placed at the specified location by checking for 
     * 		an adjacent road or settlement of the same color, 
     * 		and checking if the road is being placed on the other side of a settlement of another color
     * @param location the edge location to place the road
     * @return true if a road can be placed at the specified location
     */
    public boolean canPlace(EdgeLocation location) {
        return false;
    }
}
