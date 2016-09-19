package model;

import shared.locations.EdgeLocation;

/**
 * Specialized type of port: ResourcePort.  This class handles the trades that players make with a specific resource
 * Created by jihoon on 9/19/2016.
 */
public class ResourcePort extends Port {

    private ResourceCard.Resource portType;

    /**
     * Constructor for port
     *
     * @param location location of the selected port
     */
    public ResourcePort(EdgeLocation location, ResourceCard.Resource portType) {
        super(location);
        this.portType = portType;
    }

    /**
     * Returns the edge location where the port is located
     *
     * @return location
     */
    @Override
    public EdgeLocation getLocation() {
        return super.getLocation();
    }

    /**
     * This function checks if the player can trade with a specific port
     *
     * @return true if player can trade with this port
     */
    @Override
    public boolean canTrade() {
        return super.canTrade();
    }

    public ResourceCard.Resource getPortType() {
        return portType;
    }
}
