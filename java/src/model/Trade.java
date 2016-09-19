package model;

/**
 * Class representing a singular trade offer and the information
 *
 * Created by kcwillmore on 9/17/16.
 */
public class Trade {

    /**
     * The player who initialized the trade
     */
    private Player sender;
    
    /**
     * The player who is being offered a trade by the sender.
     * this player is the one who would ultimately choose to accept or reject this trade
     */
    private Player receiver;
    
    /**
     * The resources that the sender is offering up for trade from their resources
     */
    private Cost offer;
    
    /**
     * The resources that the sender is asking in return from the reciever's resources
     */
    private Cost request;

    /**
     * Determines if both players have the resources to make the trade
     * @return true if both players have the resources required
     */
    public boolean canTrade() {
        return false;
    }

}
