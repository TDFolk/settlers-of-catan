package model.pieces;

import model.cards_resources.ResourceCards;
import shared.definitions.CatanColor;
import shared.locations.VertexLocation;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class Settlement extends Building {
    public static final ResourceCards COST = new ResourceCards(1,0,1,1,1);

    /**
     * Settlement constructor, requires a place to go and a player/color associated
     *
     * @param color    the color of the player who is building the Building
     * @param location the vertex location where the building is to go
     */
    public Settlement(CatanColor color, VertexLocation location) {
        super(color, location);
    }


//    /**
//            *  Places a settlement at the specified VertexLocation on the map.
//            * @pre The settlement cannot be placed within two adjacent vertices of another settlement or city,
//     * 				and must be placed connected to a road of the same color
//     * @post The settlement is placed on the map at the specified VertexLocation
//     * @param location The vertex to place the settlement
//     * @throws PlacementException if the settlement cannot be placed at the specified location
//     */
//    public void place(VertexLocation location) throws PlacementException {
//
//    }
    //TODO: ask Nick are we actually changing things, or just seeing if we can?
}
