package model;

import java.util.ArrayList;
import java.util.List;

import exception.CardException;
import exception.ResourceException;
import model.ResourceCard.Resource; 
import model.development_cards.DevelopmentCard;
import model.development_cards.MonopolyCard;
import model.development_cards.PlentyCard;
import model.development_cards.RoadBuildingCard;
import model.development_cards.SoldierCard;
import model.development_cards.VictoryPointCard;

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
	 * Populates bank with starting amount of resources in each resource deck and
	 * 		the proper development cards.
	 */
	public Bank () {
		for (int i = 0; i < 19; i++) {
			oreCards.add(new ResourceCard(Resource.ORE));
			brickCards.add(new ResourceCard(Resource.BRICK));
			woodCards.add(new ResourceCard(Resource.WOOD));
			sheepCards.add(new ResourceCard(Resource.SHEEP));
			wheatCards.add(new ResourceCard(Resource.WHEAT));
		}	
		
		for (int i = 0; i < 14; i++)
			developmentCards.add(new SoldierCard());
		for (int i = 0; i < 5; i++)
			developmentCards.add(new VictoryPointCard());
		for (int i = 0; i < 2; i++) {
			developmentCards.add(new MonopolyCard());
			developmentCards.add(new PlentyCard());
			developmentCards.add(new RoadBuildingCard());
		}
	}

	/**
	 * Allows a player to draw a resource card from the bank
	 * @pre card exist
	 * @post card is removed from the resource deck
	 * @param resourceType type of resource to be drawn
	 * @throws CardException if there is no card in the deck to draw
	 * @return the resource requested by the player
	 */
	public ResourceCard drawResourceCard(Resource resourceType) throws CardException {
		List<ResourceCard> deck;
		switch (resourceType) {
		case BRICK: deck = brickCards; break;
		case ORE: deck = oreCards; break;
		case SHEEP: deck = sheepCards; break;
		case WHEAT: deck = wheatCards; break;
		case WOOD: deck = woodCards; break;
		default: return null;
		}
		
		ResourceCard card = null;
		if (deck.size() > 0)
			card = deck.remove(0);
		else throw new CardException();
		
		return card;
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
		
		if (developmentCards.size() > 0)
			card = developmentCards.remove(0);
		else throw new CardException();
		
		return card;
	}
	
	/**
	 * Determines if a card is in the specified resource type's deck, and thus can be drawn
	 * @param resourceType the resource type to draw
	 * @return true if a card can be drawn from the specified resource type's deck
	 */
	public boolean canDrawResourceCard(Resource resourceType) {
		List<ResourceCard> deck;
		switch (resourceType) {
		case BRICK: deck = brickCards; break;
		case ORE: deck = oreCards; break;
		case SHEEP: deck = sheepCards; break;
		case WHEAT: deck = wheatCards; break;
		case WOOD: deck = woodCards; break;
		default: return false;
		}
		
		return deck.size() > 0;
	}
	
	/**
	 * Determines if a card is in the development card deck, and thus can be drawn
	 * @return true if a card can be drawn from the development card deck
	 */
	public boolean canDrawDevelopmentCard() {
		return developmentCards.size() > 0;
	}
}
