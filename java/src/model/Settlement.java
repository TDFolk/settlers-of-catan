package model;

import shared.locations.VertexLocation;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class Settlement {
    private static final Cost cost = new Cost(1,0,1,1,1);

    private VertexLocation location;
    
    /**
     * Places a settlement at the specified VertexLocation on the map.
     * @pre The settlement cannot be placed within two adjacent vertices of another settlement or city, 
     * 				and must be placed connected to a road of the same color
     * @post The settlement is placed on the map at the specified VertexLocation
     * @param location The vertex to place the settlement
     */
    public void place(VertexLocation location) {
    	
    }

    /**
     * Checks if the location is a valid vertex for the settlement to be placed.
     * @param location the location to place the settlement
     * @return true if the location is a valid vertex for placement
     */
    public boolean canPlace(VertexLocation location) {
        return false;
    }
}
