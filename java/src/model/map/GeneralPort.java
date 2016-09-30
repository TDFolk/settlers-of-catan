package model.map;

import model.Game;
import model.Player;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;

/**
 * Specialized type of port: GeneralPort.  This class handles the trades that players perform with random types of resources.
 * Handles 3:1 trading
 * Created by jihoon on 9/19/2016.
 */
public class GeneralPort extends Port {

    private ResourceType portType;

    /**
     * Constructor for port
     *
     * @param location location of the selected port
     */
    public GeneralPort(EdgeLocation location, ResourceType portType) {
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
        if (!super.canTrade(player)) {
            return false;
        }
        //checks that the player has enough resources to trade 3:1
        return player.getResourceCards().getBrick() >= 3 ||
                player.getResourceCards().getOre() >=3 ||
                player.getResourceCards().getSheep() >= 3 ||
                player.getResourceCards().getWheat() >= 3 ||
                player.getResourceCards().getWood() >= 3;
    }

    public ResourceType getPortType() {
        return portType;
    }
}
