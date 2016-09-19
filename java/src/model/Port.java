package model;

import shared.locations.EdgeLocation;

/**
 * This class represents the game object, ports
 * Created by jihoon on 9/17/16.
 */
public class Port {
    private EdgeLocation location;
    /**
     * the resource type that the port can trade for at a 2:1 ratio
     * if the resource type is set to "null" then it is a generic port that instead trades 3:1
     */

    /**
     * Constructor for port
     * @param location location of the selected port
     */
    public Port(EdgeLocation location) {
        this.location = location;
    }

    /**
     * Returns the edge location where the port is located
     * @return location
     */
    public EdgeLocation getLocation() {
        return location;
    }

    /**
     * This function checks if the player can trade with a specific port
     * @return true if player can trade with this port
     */
    public boolean canTrade() {
        return false;
    }
}
