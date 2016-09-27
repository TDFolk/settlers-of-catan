package model;

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
     * @return success if robber can take the resource
     */
    public boolean canTakeResource() {
        return false;
    }
}
