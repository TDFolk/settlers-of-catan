package model;

import shared.locations.HexLocation;

/**
 * The robber steals not only resources, but friendships. Be wary of thieves.
 *
 * Created by kcwillmore on 9/17/16.
 */
public class Robber {

    private HexLocation location;

    /**
     * Robber constructor
     *
     * @param location where the robber will begin, should be a desert hex
     */
    public Robber(HexLocation location) {
        this.location = location;
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
