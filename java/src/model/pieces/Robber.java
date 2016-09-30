package model.pieces;

import model.Player;
import shared.locations.HexLocation;

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
        //todo figure out how to tell if a hex has settlements around it and whom they belong to
        return false;
    }

    public boolean canPlaceRobber(HexLocation newLocation) {
        return newLocation != location;
    }
}
