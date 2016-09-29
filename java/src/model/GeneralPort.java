package model;

import shared.locations.EdgeLocation;

/**
 * Specialized type of port: GeneralPort.  This class handles the trades that players perform with random types of resources.
 * Handles 3:1 trading
 * Created by jihoon on 9/19/2016.
 */
public class GeneralPort extends Port {

    private ResourceCard.Resource portType;

    /**
     * Constructor for port
     *
     * @param location location of the selected port
     */
    public GeneralPort(EdgeLocation location, ResourceCard.Resource portType) {
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
    public boolean canTrade(Player player) {
        return false;
    }

    public ResourceCard.Resource getPortType() {
        return portType;
    }
}
