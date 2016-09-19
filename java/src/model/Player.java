package model;

import shared.definitions.CatanColor;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

import java.util.List;

import exception.ResourceException;
import model.ResourceCard.Resource;
import model.development_cards.DevelopmentCard;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class Player {
	private String name;
    private CatanColor color;
    private List<Settlement> settlements;
    private List<City> cities;
    private List<Road> roads;
    private List<ResourceCard> resourceCards;
    private List<DevelopmentCard> developmentCards;
    private List<DevelopmentCard> newDevelopmentCards;
    private int playerID;
    private int playerIndex;
    private boolean discarded;
    private boolean playedDevCard;
    private int monuments;
    private int soldiers;
    private int victoryPoints;
    
    
    
    
    public Player() {
    	
    }
    
    
    
    
    
    public void playCard() {

    }
    
    public void drawResourceCard(Resource type) {
    	
    }
    
    public void buySettlement(VertexLocation vertex) {
    	
    }
    
    public void buyCity(VertexLocation vertex) {
    	
    }
    
    public void buyRoad(EdgeLocation edge) {
    	
    }
    
    public void buyDevelopmentCard() {
    	
    }
    
    public void playDevelopmentCard() {
    	
    }
    
    public void discard(List<ResourceCard> cards) {
    	
    }
    
    /**
     * Make a trade with another player.
     * @pre The Player in the trade must be a valid Player in the current game, and 
     * 				the Player making the trade offer must have the resources being offered
     * @post If accepted, both Players will have the other's offered resources, otherwise nothing is changed.
     * @param trade 
     * @return Returns true if the trade was accepted
     */
    public boolean makeTrade(Trade trade) throws ResourceException {
    	return false;
    }
    
    
    
    public boolean canBuyRoad() {
    	return false;
    }
    
    public boolean canBuySettlement() {
    	return false;
    }
    
    public boolean canBuyCity() {
    	return false;
    }
    
    public boolean canBuyDevelopmentCard() {
    	return false;
    }

    public boolean canPlaceSettlement(VertexLocation vertex) {
    	return false;
    }
    
    public boolean canPlaceRoad(EdgeLocation edge) {
    	return false;
    }
    
    /**
     * Determines if the player has enough resources to make the trade
     * @param offer the resources being offered for the trade
     * @return Returns true if the player has the resources specified in the offer, false otherwise.
     */
    public boolean canMakeTrade(Cost offer) {
    	return false;
    }

}
