package model.map;

import model.Game;
import model.Player;
import model.pieces.Building;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 * This class represents the game object, ports
 * Created by jihoon on 9/17/16.
 */
public class Port {
    protected EdgeLocation location;

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
    public boolean canTrade(Player player) {
        //checks that this player actually has access to this port
        return Game.getInstance().getMap().buildingByEdge(location) != null &&
                Game.getInstance().getMap().buildingByEdge(location).getColor().equals(player.getColor());
    }

}