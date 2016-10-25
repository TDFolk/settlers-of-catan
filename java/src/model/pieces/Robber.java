package model.pieces;

import model.Game;
import model.Player;
import shared.definitions.CatanColor;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 * The robber steals not only resources, but friendships. Be wary of thieves.
 *
 * Created by kcwillmore on 9/17/16.
 */
public class Robber {
    private static Robber instance = null;

    private HexLocation location;

    /**
     * Robber constructor
     */
    private Robber() {}

    public static Robber getInstance() {
        if (instance == null) {
            instance = new Robber();
        }
        return instance;
    }

    public HexLocation getLocation() {
        return location;
    }

    public void setLocation(HexLocation location) {
        this.location = location;
    }

    /**
     * Determines if the robber can rob
     * @pre none
     * @post none
     * @param victim the player you want to steal from
     * @return success if robber can take the resource
     */
    public boolean canTakeResource(Player victim) {
        //cannot steal if the victim has no cards
        if (victim.getResourceCards().size() < 1) {
            return false;
        }

        //cannot steal if there is not an adjacent settlement/city to the robber's hex
        for (VertexDirection dir : VertexDirection.values()) {
            Building adjacentBuilding = Game.getInstance().getMap().getBuildingAtVertex(new VertexLocation(location, dir));
            if (adjacentBuilding != null && adjacentBuilding.getColor() == victim.getPlayerInfo().getColor()) {
                return true;
            }
        }
        return false;
    }

    public boolean canPlaceRobber(HexLocation newLocation) {
        return newLocation != location;
    }
}
