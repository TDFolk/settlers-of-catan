package model;

import shared.locations.EdgeLocation;

/**
 * Represents the object and can do's of a port
 * Created by kcwillmore on 9/17/16.
 */
public class Port {
    private EdgeLocation location;
    /**
     * the resource type that the port can trade for at a 2:1 ratio
     * if the resource type is set to "null" then it is a generic port that instead trades 3:1
     */
    private ResourceCard.Resource portType;

    /**
     * Constructor of the port
     * @param location where the port is on the map
     * @param portType the type of resource that can be traded in the port
     */
    public Port(EdgeLocation location, ResourceCard.Resource portType) {
        this.location = location;
        this.portType = portType;
    }

    public EdgeLocation getLocation() {
        return location;
    }

    public ResourceCard.Resource getPortType() {
        return portType;
    }

    /**
     * Determines if a player can trade with the port
     * @pre none
     * @post none
     * @return success of if a player can trade with the port
     */
    public boolean canTrade() {
        return false;
    }
}
