package model;

import java.util.ArrayList;
import java.util.List;

import model.ResourceCard.Resource; 
import model.development_cards.DevelopmentCard;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class Bank {
	private List<ResourceCard> brickCards = new ArrayList<ResourceCard>();	
	private List<ResourceCard> oreCards = new ArrayList<ResourceCard>();
	private List<ResourceCard> sheepCards = new ArrayList<ResourceCard>();
	private List<ResourceCard> wheatCards = new ArrayList<ResourceCard>();
	private List<ResourceCard> woodCards = new ArrayList<ResourceCard>();
	
	private List<DevelopmentCard> developmentCards = new ArrayList<DevelopmentCard>();

	/**
	 * Constructor of the bank
	 * Populates bank with starting amount of resources in each resource deck
	 */
	public Bank () {
		for (int i = 0; i < 19; i++) {
			oreCards.add(new ResourceCard(Resource.ORE));
			brickCards.add(new ResourceCard(Resource.BRICK));
			woodCards.add(new ResourceCard(Resource.WOOD));
			sheepCards.add(new ResourceCard(Resource.SHEEP));
			wheatCards.add(new ResourceCard(Resource.WHEAT));
		}	
	}

	/**
	 * Allows a player to draw a resource card from the bank
	 * @pre card exist
	 * @post card is removed from the resource deck
	 * @param resourceType type of resource to be drawn
	 * @return the resource requested by the player
	 */
	public ResourceCard drawResourceCard(Resource resourceType) {
		return null;
	}

	/**
	 * Allows a player to draw a dev card from the bank
	 * @pre dev card exist
	 * @post dev card is removed from deck
	 * @return a random dev card back to the player
	 */
	public DevelopmentCard drawDevelopmentCard() {
		return null;
	}
	
}
