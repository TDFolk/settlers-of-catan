package client.states;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * this class is for when the players turn is over
 * Created by jihoon on 10/7/2016.
 */
public class NotMyTurnState implements IGameState{
    /**
     * This function checks whether or not in the current state, the player can build a road at a specific edge location.
     *
     * @param edgeLocation The edge location the player wants to place their road on.
     * @return true if the player CAN place the road.
     */
    @Override
    public boolean canBuildRoad(EdgeLocation edgeLocation) {
        return false;
    }

    /**
     * This function checks whether or not in the current state, the player can build a settlement at the specified vertex location.
     *
     * @param vertexLocation The vertex location where the player wants to place their settlement on.
     * @return true if the player CAN place the settlement.
     */
    @Override
    public boolean canBuildSettlement(VertexLocation vertexLocation) {
        return false;
    }

    /**
     * This function checks whether or not in the current state, the player can build a city on top of their settlement at a specified vertex location.
     *
     * @param vertexLocation The vertex location where the player wants their city on.
     * @return true if the player CAN build the city.
     */
    @Override
    public boolean canBuildCity(VertexLocation vertexLocation) {
        return false;
    }

    /**
     * This function will check if the robber can be placed in the selected hex location.
     *
     * @param hexLocation The hex location where the player wants to place the robber on.
     * @return true if the player CAN place the robber at the selected location.
     */
    @Override
    public boolean canPlaceRobber(HexLocation hexLocation) {
        return false;
    }

    /**
     * This function will return the current state name.
     *
     * @return the state the game is on.
     */
    @Override
    public String getState() {
        return null;
    }
}
