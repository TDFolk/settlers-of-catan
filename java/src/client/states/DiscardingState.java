package client.states;

import model.Game;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * This class handles the state when the game is in the discard state
 * Created by jihoon on 10/7/2016.
 */
public class DiscardingState implements IGameState{

//    private Game game;
//
//    public DiscardingState(Game game){
//        this.game = game;
//    }


    /**
     * This function checks whether within this state, the player has permission to buy roads/buildings/devCards
     *
     * @return true if the player may purchase buildings and devcards
     */
    @Override
    public boolean canMakePurchases() {
        return false;
    }

    /**
     * This function checks whether, in this state, the player may play their development cards.
     * Note that this does not consider whether or not the player has already played a development card this turn
     *
     * @return true if the player may play development cards
     */
    @Override
    public boolean canPlayDevCards() {
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
