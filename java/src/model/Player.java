package model;

import model.cards_resources.ResourceCards;
import model.cards_resources.Trade;
import model.pieces.City;
import model.pieces.Road;
import model.pieces.Settlement;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

import java.util.ArrayList;
import java.util.List;

import exception.CardException;
import exception.PlacementException;
import exception.ResourceException;
import model.cards_resources.DevelopmentCard;

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
    	resourceCards = new ResourceCards(0,0,0,0,0);
        developmentCards = new ArrayList<>();
        victoryPoints = 0;
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
        Game.getInstance().getBank().drawResourceCard(type);
        int numberOfResource = resourceCards.getResource(type);
        resourceCards.setResource(type, numberOfResource + 1);
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
        if (canBuySettlement()) {
            resourceCards.reduceResources(Settlement.COST);
            Game.getInstance().getBank().purchaseSettlement();
        }
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
        if (canBuyCity()) {
            resourceCards.reduceResources(City.COST);
            Game.getInstance().getBank().purchaseCity();
        }
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
    	if (canBuyRoad()) {
    	    resourceCards.reduceResources(Road.COST);
            Game.getInstance().getBank().purchaseRoad();
        }
    }
    
    /**
     * Buys a development card and adds it to the player's list of development cards
     * @pre The player has enough resources to buy a development card
     * @post The player has a new development card and has spent the resource cards required
     * @throws ResourceException if the player does not have the resources to buy a development card
     */
    public void buyDevelopmentCard() throws ResourceException, CardException {
    	if (canBuyDevelopmentCard()) {
    	    resourceCards.reduceResources(DevelopmentCard.COST);
            Game.getInstance().getBank().purchaseDevCard();
            developmentCards.add(Game.getInstance().getBank().drawDevelopmentCard());
        }
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
    public void discard(ResourceCards cards) throws CardException {
    	
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
    	return resourceCards.canPay(Road.COST);
    }
    
    /**
     * Determines if the player has the resources to buy a settlement
     * @return true if the player has the resources to buy a settlement
     */
    public boolean canBuySettlement() {
        return resourceCards.canPay(Settlement.COST);
    }
    
    /**
     * Determines if the player has the resources to buy a city
     * @return true if the player has the resources to buy a city
     */
    public boolean canBuyCity() {
        return resourceCards.canPay(City.COST);
    }
    
    /**
     * Determines if the player has the resources to buy a development card
     * @return true if the player has the resources to buy a development card
     */
    public boolean canBuyDevelopmentCard() {
    	return resourceCards.canPay(DevelopmentCard.COST);
    }
    
    /**
     * Determines if the player owns the specified development card and can play it
     * @param card the card to play
     * @return true if the player owns and can play the card
     */
    public boolean canPlayDevelopmentCard(DevCardType card) {
        for (int i = 0; i < developmentCards.size(); i++) {
            if (developmentCards.get(i).getType() == card) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Determines if the player owns the cards to be discarded
     * @param cards the cards to be discarded
     * @return true if the player owns the cards and can discard them
     */
    public boolean canDiscard(ResourceCards cards) {
        return resourceCards.canPay(cards);
    }

    /**
     * Determines if a settlement can be placed at the specified location, such that the settlement is not  
     * 			    within two adjacent vertices of another settlement and is placed adjacent to a
     * 				road of the same color.
     * @param vertex the location to place the settlement
     * @return true if a settlement can be placed at the specified location
     */
    public boolean canPlaceSettlement(VertexLocation vertex) {
    	return true;
    }
    
    /**
     * Determines if a road can be placed at the specified location, such that the road is connected
     * 				to another road or building of the same color, and does not pass through a building 
     * 				of an opposing color
     * @param edge the location to place the settlement
     * @return true if a settlement can be placed at the specified location
     */
    public boolean canPlaceRoad(EdgeLocation edge) {
    	return true;
    }
    
    /**
     * Determines if the player has enough resources to make the trade
     * @param offer the resources being offered for the trade
     * @return Returns true if the player has the resources specified in the offer, false otherwise.
     */

    public boolean canMakeTrade(ResourceCards offer) {
    	return resourceCards.canPay(offer);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(CatanColor color) {
        this.color = color;
    }

    public List<Settlement> getSettlements() {
        return settlements;
    }

    public void setSettlements(List<Settlement> settlements) {
        this.settlements = settlements;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }

    public void setResourceCards(ResourceCards resourceCards) {
        this.resourceCards = resourceCards;
    }

    public List<DevelopmentCard> getDevelopmentCards() {
        return developmentCards;
    }

    public void setDevelopmentCards(List<DevelopmentCard> developmentCards) {
        this.developmentCards = developmentCards;
    }

    public List<DevelopmentCard> getNewDevelopmentCards() {
        return newDevelopmentCards;
    }

    public void setNewDevelopmentCards(List<DevelopmentCard> newDevelopmentCards) {
        this.newDevelopmentCards = newDevelopmentCards;
    }

    public void setPlayerID(PlayerID playerID) {
        this.playerID = playerID;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
    }

    public boolean isPlayedDevCard() {
        return playedDevCard;
    }

    public void setPlayedDevCard(boolean playedDevCard) {
        this.playedDevCard = playedDevCard;
    }

    public int getMonuments() {
        return monuments;
    }

    public void setMonuments(int monuments) {
        this.monuments = monuments;
    }

    public int getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(int soldiers) {
        this.soldiers = soldiers;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
