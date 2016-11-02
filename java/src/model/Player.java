package model;

import client.data.PlayerInfo;
//import model.map.GeneralPort;
import client.states.FirstRoundState;
import model.map.Port;
import model.cards_resources.ResourceCards;
import model.cards_resources.Trade;
//import model.map.ResourcePort;
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

    private int settlements;
    private int cities;
    private int roads;
    private ResourceCards resourceCards;
    private List<DevelopmentCard> developmentCards;
    private List<DevelopmentCard> newDevelopmentCards;
    private boolean discarded;
    private boolean playedDevCard;
    private int monuments;
    private int soldiers;
    private int victoryPoints;
    private PlayerInfo playerInfo;    
    
    
    /**
     * Constructs a new player for the start of a game
     */
    public Player() {
        roads = 15;
        settlements = 5;
        cities = 4;
        resourceCards = new ResourceCards(0,0,0,0,0);
        developmentCards = new ArrayList<>();
        victoryPoints = 0;

    }


    public Player(String name, CatanColor color, int settlements, int cities, int roads,
                  ResourceCards resourceCards, List<DevelopmentCard> developmentCards,
                  List<DevelopmentCard> newDevelopmentCards, PlayerID playerID, int playerIndex, boolean discarded,
                  boolean playedDevCard, int monuments, int soldiers, int victoryPoints) {

        this.settlements = settlements;
        this.cities = cities;
        this.roads = roads;
        this.resourceCards = resourceCards;
        this.developmentCards = developmentCards;
        this.newDevelopmentCards = newDevelopmentCards;
        this.discarded = discarded;
        this.playedDevCard = playedDevCard;
        this.monuments = monuments;
        this.soldiers = soldiers;
        this.victoryPoints = victoryPoints;

        this.playerInfo = new PlayerInfo();
        this.playerInfo.setName(name);
        this.playerInfo.setColor(color);
        this.playerInfo.setId(playerID.getID());
        this.playerInfo.setPlayerIndex(playerIndex);

    }

    public boolean overResourceLimit() {
        return resourceCards.size() > 7;
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
            newDevelopmentCards.add(Game.getInstance().getBank().drawDevelopmentCard());
        }
    }
    
    public boolean canPlayDevelopmentCard(DevCardType card) {
    	for (DevelopmentCard devCard : developmentCards) {
    		if (devCard.getType() == card) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Plays the development card specified and brings about its effects
     * @pre Player must own the card specified
     * @post Player no longer owns the card, the playedDevCard flag is changed, and the effects of the card are done
     * @param card the card to play
     * @throws CardException if the player does not own the card
     */
    public void playDevelopmentCard(DevCardType card) throws CardException {
    	for (DevelopmentCard devCard : developmentCards) {
    		if (devCard.getType() == card) {
    			developmentCards.remove(devCard);
    			return;
    		}
    	}
    	throw new CardException();
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
    	return roads > 0 && resourceCards.canPay(Road.COST);
    }
    
    /**
     * Determines if the player has the resources to buy a settlement
     * @return true if the player has the resources to buy a settlement
     */
    public boolean canBuySettlement() {
        return settlements > 0 && resourceCards.canPay(Settlement.COST);
    }
    
    /**
     * Determines if the player has the resources to buy a city
     * @return true if the player has the resources to buy a city
     */
    public boolean canBuyCity() {
        return cities > 0 && resourceCards.canPay(City.COST);
    }
    
    /**
     * Determines if the player has the resources to buy a development card
     * @return true if the player has the resources to buy a development card
     */
    public boolean canBuyDevelopmentCard() {
    	return resourceCards.canPay(DevelopmentCard.COST);
    }
    
    /**
     * Determines if the player can play a development card, ie that they havn't already played one this turn, and that
     * they possess at least one card
     * @return true if the player owns and can play a card
     */
    public boolean canPlayDevelopmentCards() {
        return !playedDevCard && developmentCards.size() > 0;
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
    	if (settlements > 0) {
    	    if (Game.getInstance().getTurnTracker().getStatus().equals("FirstRound") ||
                    Game.getInstance().getTurnTracker().getStatus().equals("SecondRound")){
                return Game.getInstance().getMap().canPlaceSettlement(vertex, this);
            }
    	    else if (canBuySettlement()) {
                return Game.getInstance().getMap().canPlaceSettlement(vertex, this);
            }
        }
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
    	if (roads > 0) {
            return Game.getInstance().getMap().canPlaceRoad(edge, this);
        }
        return false;
    }

    /**
     * Determines if a player can maritime trade with port of the given resource.
     * Does not work with null port, only resource ports
     * @param resource
     * @return
     */
    public boolean hasPortTradeResources(ResourceType resource) {
        return resourceCards.getResource(resource) >= 2;
    }

    /**
     * CHeck to see if a player has at least one resource at or above the given value
     * for checking if user can do 3:1 or 4:1 trading
     * @param quantity how many of a resource at least one of the user's resources must have
     * @return true if the player has at least quantity of any one of their resources
     */
    public boolean hasResourcesOfQuantity(int quantity) {
        return resourceCards.getBrick() >= quantity ||
                resourceCards.getOre() >= quantity ||
                resourceCards.getSheep() >= quantity ||
                resourceCards.getWheat() >= quantity ||
                resourceCards.getWood() >= quantity;
    }

    /**
     * Determines if the player has enough resources to make the trade
     * @param offer the resources being offered for the trade
     * @return Returns true if the player has the resources specified in the offer, false otherwise.
     */

    public boolean canMakeTrade(ResourceCards offer) {
    	return resourceCards.canPay(offer);
    }


    public int getSettlements() {
        return settlements;
    }

    public void setSettlements(int settlements) {
        this.settlements = settlements;
    }

    public int getCities() {
        return cities;
    }

    public void setCities(int cities) {
        this.cities = cities;
    }

    public int getRoads() {
        return roads;
    }

    public void setRoads(int roads) {
        this.roads = roads;
    }

    public void setResourceCards(ResourceCards resourceCards) {
        this.resourceCards = resourceCards;
    }
    
    public void acceptCards(ResourceCards cards) {
    	this.resourceCards.increaseResources(cards);
    }
    
    public void forfeitCards(ResourceCards cards) {
    	this.resourceCards.reduceResources(cards);
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
    
    public void addMonument() {
    	this.monuments++;
    }

    public int getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(int soldiers) {
        this.soldiers = soldiers;
    }
    
    public void addSoldier() {
    	this.soldiers++;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }


    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }
}
