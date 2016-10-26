package model.cards_resources;

import model.Game;
import model.Player;

/**
 * Class representing a singular trade offer and the information
 *
 * Created by Brandon Oct 25th boi..
 */
public class Trade {

    /**
     * The player (index) who initialized the trade
     */
    private int sender;
    
    /**
     * The player (index) who is being offered a trade by the sender.
     * this player is the one who would ultimately choose to accept or reject this trade
     */
    private int receiver;
    
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
    public Trade(int sender, int receiver, ResourceCards offer, ResourceCards request) {
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
        return Game.getInstance().getPlayersList().get(sender).getResourceCards().canPay(offer)
                && Game.getInstance().getPlayersList().get(receiver).getResourceCards().canPay(request);
    }
    
    public void executeTrade() {
        Player send = Game.getInstance().getPlayersList().get(sender);
        Player receive = Game.getInstance().getPlayersList().get(receiver);

    	send.forfeitCards(offer);
    	receive.forfeitCards(request);
    	
    	send.acceptCards(request);
    	receive.acceptCards(offer);
    }

}
