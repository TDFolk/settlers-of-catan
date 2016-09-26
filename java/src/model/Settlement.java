package model;

import exception.PlacementException;
import shared.definitions.CatanColor;
import shared.locations.VertexLocation;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class Settlement extends Building {
    private static final ResourceValues RESOURCE_VALUES = new ResourceValues(1,0,1,1,1);

    /**
     * Settlement constructor, requires a place to go and a player/color associated
     *
     * @param color    the color of the player who is building the Building
     * @param location the vertex location where the building is to go
     */
    public Settlement(CatanColor color, VertexLocation location) {
        super(color, location);
    }


    /**
     * Places a settlement at the specified VertexLocation on the map.
     * @pre The settlement cannot be placed within two adjacent vertices of another settlement or city, 
     * 				and must be placed connected to a road of the same color
     * @post The settlement is placed on the map at the specified VertexLocation
     * @param location The vertex to place the settlement
     * @throws PlacementException if the settlement cannot be placed at the specified location
     */
    public void place(VertexLocation location) throws PlacementException {
    	
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
