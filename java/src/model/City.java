package model;

import shared.locations.VertexLocation;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class City extends Building {
    private static final Cost cost = new Cost(0,3,0,2,0);

    private VertexLocation location;
    
    /**
     * Replaces a Settlement on the Map with a City instead
     * @pre There must be a Settlement of the same color at the specified location
     * @post The Settlement is replaced with a City at the specified VertexLocation
     * @param location The vertex to place the City
     */
    public void upgradeSettlement(VertexLocation location) {
    	
    }

    /**
     * Determines if the specified vertex location has a settlement of the same color, 
     * 		thus being a valid location to place the City
     * @param location the location to place the City
     * @return true if a city can be bought for the specified location
     */
    public boolean canUpgradeSettlement(VertexLocation location) {
        return false;
    }
}
