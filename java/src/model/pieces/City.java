package model.pieces;

import model.ResourceCards;
import shared.definitions.CatanColor;
import shared.locations.VertexLocation;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class City extends Building {
<<<<<<< HEAD
    public static final ResourceValues COST = new ResourceValues(0,3,0,2,0);
=======
    private static final ResourceCards COST = new ResourceCards(0,3,0,2,0);
>>>>>>> origin/master

    /**
     * City constructor
     *
     * @param color the color of the player putting the city down
     * @param location the vertex where the city is to go
     */
    public City(CatanColor color, VertexLocation location) {
        super(color, location);
    }

//    /**
//     * Replaces a Settlement on the map with a City instead
//     * @pre There must be a Settlement of the same color at the specified location
//     * @post The Settlement is replaced with a City at the specified VertexLocation
//     * @param location The vertex to place the City
//     * @throws PlacementException if the City cannot be placed at the specified location
//     */
//    public void upgradeSettlement(VertexLocation location) throws PlacementException {
//
//    }
//
//    /**
//     * Determines if the specified vertex location has a settlement of the same color,
//     * 		thus being a valid location to place the City
//     * @param location the location to place the City
//     * @return true if a city can be bought for the specified location
//     */
//    public boolean canUpgradeSettlement(VertexLocation location) {
//        return false;
//    }

    // I think that the above two functions are pointless, and so commented them out. TODO: second opinion from nick
}
