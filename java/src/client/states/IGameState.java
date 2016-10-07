package client.states;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * This is the state interface for the main six phases of the game
 * Created by jihoon on 10/7/2016.
 */
public interface IGameState {

    /**
     * This function checks whether or not in the current state, the player can build a road at a specific edge location.
     * @param edgeLocation The edge location the player wants to place their road on.
     * @return true if the player CAN place the road.
     */
    public boolean canBuildRoad(EdgeLocation edgeLocation);

    /**
     * This function checks whether or not in the current state, the player can build a settlement at the specified vertex location.
     * @param vertexLocation The vertex location where the player wants to place their settlement on.
     * @return true if the player CAN place the settlement.
     */
    public boolean canBuildSettlement(VertexLocation vertexLocation);

    /**
     * This function checks whether or not in the current state, the player can build a city on top of their settlement at a specified vertex location.
     * @param vertexLocation The vertex location where the player wants their city on.
     * @return true if the player CAN build the city.
     */
    public boolean canBuildCity(VertexLocation vertexLocation);

    /**
     * This function will check if the robber can be placed in the selected hex location.
     * @param hexLocation The hex location where the player wants to place the robber on.
     * @return true if the player CAN place the robber at the selected location.
     */
    public boolean canPlaceRobber(HexLocation hexLocation);

    /**
     * This function will return the current state name.
     * @return the state the game is on.
     */
    public String getState();
}
