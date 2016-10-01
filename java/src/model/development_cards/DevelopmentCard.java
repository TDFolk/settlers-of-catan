package model.development_cards;

import model.ResourceCards;

/**
 * Created by kcwillmore on 9/17/16.
 */
public abstract class DevelopmentCard {

    private static final ResourceCards RESOURCE_VALUES = new ResourceCards(0,1,1,1,0);

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
