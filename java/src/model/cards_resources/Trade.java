package model.cards_resources;

import model.Player;

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
    private ResourceCards offer;
    
    /**
     * The resources that the sender is asking in return from the reciever's resources
     */
    private ResourceCards request;

    /**
     * Trade constructor, makes a trade offer between two players and what each one would give the other in exchange
     *
     * @param sender the player initializing the trade
     * @param receiver the player who will accept or reject the trade
     * @param offer what the sender is offering up from their resources
     * @param request what the sender expects in return from the recipient
     */
    public Trade(Player sender, Player receiver, ResourceCards offer, ResourceCards request) {
        this.sender = sender;
        this.receiver = receiver;
        this.offer = offer;
        this.request = request;
    }

    /**
     * Determines if both players have the resources to make the trade
     * @return true if both players have the resources required
     */
    public boolean canTrade() {
        return sender.getResourceCards().canPay(offer) && receiver.getResourceCards().canPay(request);
    }

}
