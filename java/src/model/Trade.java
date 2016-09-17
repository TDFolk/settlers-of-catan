package model;

/**
 * Class representing a singular trade offer and the information
 *
 * Created by kcwillmore on 9/17/16.
 */
public class Trade {

    /**
     * the player who initialized the trade
     */
    Player sender;
    /**
     * the player who is being offered a trade by the sender
     * this player is the one who would ultimately choose to accept or reject this trade
     */
    Player receiver;
    /**
     * the resources that the sender is offering up for trade from their resources
     */
    Cost offer;
    /**
     * the resources that the sender is asking in return from the reciever's resources
     */
    Cost request;

}
