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
	
	public Bank () {
		for (int i = 0; i < 19; i++) {
			oreCards.add(new ResourceCard(Resource.ORE));
			brickCards.add(new ResourceCard(Resource.BRICK));
			woodCards.add(new ResourceCard(Resource.WOOD));
			sheepCards.add(new ResourceCard(Resource.SHEEP));
			wheatCards.add(new ResourceCard(Resource.WHEAT));
		}	
	}
	
	public ResourceCard drawResourceCard() {
		return null;
	}
	
	public DevelopmentCard drawDevelopmentCard() {
		return null;
	}
	
}
