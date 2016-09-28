package model;

import shared.locations.EdgeLocation;

/**
 * This class represents the game object, ports
 * Created by jihoon on 9/17/16.
 */
public abstract class Port {
    private EdgeLocation location;

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
    public abstract boolean canTrade();
}