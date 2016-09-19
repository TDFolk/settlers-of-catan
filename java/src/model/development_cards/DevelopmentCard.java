package model.development_cards;

import model.Cost;

/**
 * Created by kcwillmore on 9/17/16.
 */
public abstract class DevelopmentCard {

    private static final Cost cost = new Cost(0,1,1,1,0);

    /**
     * Checks the current game model and determines if a player meets the requirements to play a dev card
     * @pre none
     * @post none
     * @return success of if the current game model allows the play of a dev card
     */
    public boolean canPlay() {
        return false;
    }
    
    
}
