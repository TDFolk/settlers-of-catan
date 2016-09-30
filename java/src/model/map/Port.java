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
    private VertexLocation tradespot1;
    private VertexLocation tradespot2;

    /**
     * Constructor for port
     * @param location location of the selected port
     */
    public Port(EdgeLocation location) {
        this.location = location;
        calculateTradespots();
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
        return getTradeBuilding() != null && getTradeBuilding().getColor().equals(player.getColor());
    }

    /**
     * figures out the vertices adjacent tot his port
     * might be better off in the map, if other classes could use such a thing
     */
    private void calculateTradespots() {
        VertexDirection dir1 = null;
        VertexDirection dir2 = null;
        switch (location.getDir()) {
            case NorthWest:
                dir1 = VertexDirection.West;
                dir2 = VertexDirection.NorthWest;
            case North:
                dir1 = VertexDirection.NorthWest;
                dir2 = VertexDirection.NorthEast;
            case NorthEast:
                dir1 = VertexDirection.NorthEast;
                dir2 = VertexDirection.East;
            case SouthWest:
                dir1 = VertexDirection.West;
                dir2 = VertexDirection.SouthWest;
            case South:
                dir1 = VertexDirection.SouthWest;
                dir2 = VertexDirection.SouthEast;
            case SouthEast:
                dir1 = VertexDirection.East;
                dir2 = VertexDirection.SouthEast;
        }
        tradespot1 = new VertexLocation(location.getHexLoc(), dir1);
        tradespot2 = new VertexLocation(location.getHexLoc(), dir2);
    }

    /**
     * Finds out if a city or settlement is built on the tradespots of this port
     *
     * @return the building adjacent to this port, null if none is found
     */
    private Building getTradeBuilding() {
        Building building = Game.getInstance().getMap().getBuildingAtVertex(tradespot1);
        if (building == null) {
            building = Game.getInstance().getMap().getBuildingAtVertex(tradespot2);
        }
        return building;
    }
}