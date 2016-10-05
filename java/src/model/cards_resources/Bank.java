package model.cards_resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import exception.CardException;
import model.pieces.City;
import model.pieces.Road;
import model.pieces.Settlement;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class Bank {
	private ResourceCards resourcePool;
	private List<DevelopmentCard> developmentCards = new ArrayList<DevelopmentCard>();

	/**
	 * Constructor of the bank
	 * Populates bank with starting amount of resources in each resource deck and
	 * 		the proper development cards.
	 */
	public Bank () {
		resourcePool = new ResourceCards(19,19,19,19,19);
		
		for (int i = 0; i < 14; i++)
			developmentCards.add(new DevelopmentCard(DevCardType.SOLDIER));
		for (int i = 0; i < 5; i++)
			developmentCards.add(new DevelopmentCard(DevCardType.MONUMENT));
		for (int i = 0; i < 2; i++) {
			developmentCards.add(new DevelopmentCard(DevCardType.MONOPOLY));
			developmentCards.add(new DevelopmentCard(DevCardType.YEAR_OF_PLENTY));
			developmentCards.add(new DevelopmentCard(DevCardType.ROAD_BUILD));
		}
	}

	public Bank(ResourceCards resourcePool, List<DevelopmentCard> devCards){

		this.resourcePool = resourcePool;
		this.developmentCards = devCards;

	}

	/**
	 * Allows a player to draw a resource card from the bank
	 * @pre card exist
	 * @post card is removed from the resource deck
	 * @param resourceType type of resource to be drawn
	 * @throws CardException if there is no card in the deck to draw
	 * @return if the draw was successful
	 */
	public boolean drawResourceCard(ResourceType resourceType) throws CardException {
		int numResources = resourcePool.getResource(resourceType);
		if (numResources > 0) {
			resourcePool.setResource(resourceType, numResources - 1);
			return true;
		}
		throw new CardException();
	}

	/**
	 * Allows a player to draw a dev card from the bank
	 * @pre dev card exist
	 * @post dev card is removed from deck
	 * @throws CardException if there is no card in the deck to draw
	 * @return a random dev card back to the player
	 */
	public DevelopmentCard drawDevelopmentCard() throws CardException {
		DevelopmentCard card = null;
		
		if (developmentCards.size() > 0) {
			long seed = System.nanoTime();
			Collections.shuffle(developmentCards, new Random(seed));
			card = developmentCards.remove(0);
		}
		else throw new CardException();
		
		return card;
	}

	
	/**
	 * Determines if a card is in the specified resource type's deck, and thus can be drawn
	 * @param resourceType the resource type to draw
	 * @return true if a card can be drawn from the specified resource type's deck
	 */
	public boolean canDrawResourceCard(ResourceType resourceType) {
		return resourcePool.getResource(resourceType) > 0;
	}
	
	/**
	 * Determines if a card is in the development card deck, and thus can be drawn
	 * @return true if a card can be drawn from the development card deck
	 */
	public boolean canDrawDevelopmentCard() {
		return developmentCards.size() > 0;
	}

	public void purchaseCity() {
		resourcePool.increaseResources(City.COST);
	}

	public void purchaseSettlement() {
		resourcePool.increaseResources(Settlement.COST);
	}

	public void purchaseRoad() {
		resourcePool.increaseResources(Road.COST);
	}

	public void purchaseDevCard() {
		resourcePool.increaseResources(DevelopmentCard.COST);
	}

	public List<DevelopmentCard> getDevelopmentCards() {
		return developmentCards;
	}

	public ResourceCards getResourcePool() {
		return resourcePool;
	}

	public void setResourcePool(ResourceCards resourcePool) {
		this.resourcePool = resourcePool;
	}

	public void setDevelopmentCards(List<DevelopmentCard> developmentCards) {
		this.developmentCards = developmentCards;
	}
}
