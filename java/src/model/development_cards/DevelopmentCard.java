package model.development_cards;

<<<<<<< HEAD
import model.ResourceValues;
import shared.definitions.DevCardType;
=======
import model.ResourceCards;
>>>>>>> origin/master

/**
 * Created by kcwillmore on 9/17/16.
 */
public class DevelopmentCard {

<<<<<<< HEAD
    public static final ResourceValues COST = new ResourceValues(0,1,1,1,0);

    private DevCardType type;

    public DevelopmentCard(DevCardType type) {
        this.type = type;
    }

    public DevelopmentCard() {}
=======
    private static final ResourceCards RESOURCE_VALUES = new ResourceCards(0,1,1,1,0);
>>>>>>> origin/master

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
