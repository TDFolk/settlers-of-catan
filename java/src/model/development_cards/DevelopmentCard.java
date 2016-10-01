package model.development_cards;

import model.ResourceValues;
import shared.definitions.DevCardType;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class DevelopmentCard {

    public static final ResourceValues COST = new ResourceValues(0,1,1,1,0);

    private DevCardType type;

    public DevelopmentCard(DevCardType type) {
        this.type = type;
    }

    public DevelopmentCard() {}

    /**
     * Checks the current game model and determines if a player meets the requirements to play a dev card
     * @pre none
     * @post none
     * @return success of if the current game model allows the play of a dev card
     */

    public DevCardType getType() {
        return type;
    }

    public void setType(DevCardType type) {
        this.type = type;
    }

    public boolean canPlay() {
        return true;
    }

    
}
