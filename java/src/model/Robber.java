package model;

import shared.locations.HexLocation;

/**
 * The robber steals not only resources, but friendships. Be wary of thieves.
 *
 * Created by kcwillmore on 9/17/16.
 */
public class Robber {

    HexLocation location;

    public Robber(HexLocation location) {
        this.location = location;
    }

    public HexLocation getLocation() {
        return location;
    }

    public void setLocation(HexLocation location) {
        this.location = location;
    }
}
