package model;

import model.pieces.City;
import model.pieces.Road;
import model.pieces.Settlement;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

import java.util.List;

import exception.CardException;
import exception.PlacementException;
import exception.ResourceException;
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
    private ResourceCards resourceCards;
    private List<DevelopmentCard> developmentCards;
    private List<DevelopmentCard> newDevelopmentCards;
    private PlayerID playerID;
    private int playerIndex;
    private boolean discarded;
    private boolean playedDevCard;
    private int monuments;
    private int soldiers;
    private int victoryPoints;
    
    
    
    /**
     * Constructs a new player for the start of a game
     */
    public Player() {
    	
    }

    public ResourceCards getResourceCards() {
        return resourceCards;
    }

    /**
     * Takes a resource card from the bank and adds it to the player's list of resource cards
     * @pre A card exists in the bank's resource deck of that type
     * @post A card is added to the player's list of cards
     * @throws CardException if there is no card in the bank's resource deck
     * @param type the type of resource card to draw
     */
    public void drawResourceCard(ResourceType type) throws CardException {
    	
    }
    
    /**
     * Buys a settlement and places it on the specified location
     * @pre Player must have the resources to cover the cost of the settlement, and the settlement cannot 
     * 				be placed within two adjacent vertices of another settlement, and must be placed on a
     * 				road of the same color.
     * @post The settlement is placed on the specified location, and the player has spent the required resources
     * @param vertex the location to place the settlement
     * @throws ResourceException if the player does not have enough resources to buy a settlement
     * @throws PlacementException if the settlement cannot be placed at the specified location
     */
    public void buySettlement(VertexLocation vertex) throws ResourceException, PlacementException {
    	
    }
    
    /**
     * Buys a City and places it on the specified location
     * @pre Player must have the resources to cover the cost of the city, and the city must replace a 
     * 				settlement of the same color.
     * @post The city is placed on the specified location, replacing the settlement, 
     * 				and the player has spent the required resources
     * @param vertex the location to place the city
     * @throws ResourceException if the player does not have enough resources to buy a city
     * @throws PlacementException if the city cannot be placed at the specified location
     */
    public void buyCity(VertexLocation vertex) throws ResourceException, PlacementException {
    	
    }
    
    /**
     * Buys a Road and places it on the specified location
     * @pre Player must have the resources to cover the cost of the road, and the road must be placed connected
     * 				to another road or building of the same color, and cannot pass through a building of an
     * 				opposing color
     * @post The road is placed on the specified location, and the player has spent the required resources
     * @param edge the location to place the road
     * @throws ResourceException if the player does not have enough resources to buy a road
     * @throws PlacementException if the road cannot be placed at the specified location
     */
    public void buyRoad(EdgeLocation edge) throws ResourceException, PlacementException {
    	
    }
    
    /**
     * Buys a development card and adds it to the player's list of development cards
     * @pre The player has enough resources to buy a development card
     * @post The player has a new development card and has spent the resource cards required
     * @throws ResourceException if the player does not have the resources to buy a development card
     */
    public void buyDevelopmentCard() throws ResourceException {
    	
    }
    
    /**
     * Plays the development card specified and brings about its effects
     * @pre Player must own the card specified
     * @post Player no longer owns the card, the playedDevCard flag is changed, and the effects of the card are done
     * @param card the card to play
     * @throws CardException if the player does not own the card
     */
    public void playDevelopmentCard(DevelopmentCard card) throws CardException {
    	
    }
    
    /**
     * Discards the specified cards from the player's resource card list
     * @pre The player must own the cards in the list of cards to be discarded
     * @post The cards are removed from the player's card list and added to the Bank's decks
     * @param cards the cards to discard
     * @throws CardException if the player does not own the cards specified
     */
    public void discard(List<ResourceCard> cards) throws CardException {
    	
    }
    
    /**
     * Make a trade with another player.
     * @pre The Player in the trade must be a valid Player in the current game, and 
     * 				the Player making the trade offer must have the resources being offered
     * @post If accepted, both Players will have the other's offered resources, otherwise nothing is changed.
     * @param trade 
     * @throws ResourceException if one of the players does not have the resources that the trade requires
     * @return Returns true if the trade was accepted
     */
    public boolean makeTrade(Trade trade) throws ResourceException {
    	return false;
    }
    
    /**
     * Determines if the player has the resources to buy a road
     * @return true if the player has the resources to buy a road
     */
    public boolean canBuyRoad() {
    	return false;
    }
    
    /**
     * Determines if the player has the resources to buy a settlement
     * @return true if the player has the resources to buy a settlement
     */
    public boolean canBuySettlement() {
    	return false;
    }
    
    /**
     * Determines if the player has the resources to buy a city
     * @return true if the player has the resources to buy a city
     */
    public boolean canBuyCity() {
    	return false;
    }
    
    /**
     * Determines if the player has the resources to buy a development card
     * @return true if the player has the resources to buy a development card
     */
    public boolean canBuyDevelopmentCard() {
    	return false;
    }
    
    /**
     * Determines if the player owns the specified development card and can play it
     * @param card the card to play
     * @return true if the player owns and can play the card
     */
    public boolean canPlayDevelopmentCard(DevelopmentCard card) {
    	return false;
    }
    
    /**
     * Determines if the player owns the cards to be discarded
     * @param cards the cards to be discarded
     * @return true if the player owns the cards and can discard them
     */
    public boolean canDiscard(List<ResourceCard> cards) {
    	return false;
    }

    /**
     * Determines if a settlement can be placed at the specified location, such that the settlement is not  
     * 			    within two adjacent vertices of another settlement and is placed adjacent to a
     * 				road of the same color.
     * @param vertex the location to place the settlement
     * @return true if a settlement can be placed at the specified location
     */
    public boolean canPlaceSettlement(VertexLocation vertex) {
    	return false;
    }
    
    /**
     * Determines if a road can be placed at the specified location, such that the road is connected
     * 				to another road or building of the same color, and does not pass through a building 
     * 				of an opposing color
     * @param edge the location to place the settlement
     * @return true if a settlement can be placed at the specified location
     */
    public boolean canPlaceRoad(EdgeLocation edge) {
    	return false;
    }
    
    /**
     * Determines if the player has enough resources to make the trade
     * @param offer the resources being offered for the trade
     * @return Returns true if the player has the resources specified in the offer, false otherwise.
     */
    public boolean canMakeTrade(ResourceCards offer) {
    	return false;
    }
    
    
    public int getPlayerID() {
    	return playerID.getID();
    }
    
    public void setPlayerIndex(int index) {
    	playerIndex = index;
    }
    
    public int getPlayerIndex() {
    	return playerIndex;
    }

    public CatanColor getColor() {
        return color;
    }
}
