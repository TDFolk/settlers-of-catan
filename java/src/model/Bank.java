package model;

import java.util.ArrayList;
import java.util.List;

import model.ResourceCard.Resource; 

/**
 * Created by kcwillmore on 9/17/16.
 */
public class Bank {
	private List<ResourceCard> stoneCards = new ArrayList<ResourceCard>();
	private List<ResourceCard> brickCards = new ArrayList<ResourceCard>();	
	private List<ResourceCard> woodCards = new ArrayList<ResourceCard>();
	private List<ResourceCard> sheepCards = new ArrayList<ResourceCard>();
	private List<ResourceCard> wheatCards = new ArrayList<ResourceCard>();
	
	public Bank () {
		for (int i = 0; i < 19; i++) {
			stoneCards.add(new ResourceCard(Resource.STONE));
			brickCards.add(new ResourceCard(Resource.BRICK));
			woodCards.add(new ResourceCard(Resource.WOOD));
			sheepCards.add(new ResourceCard(Resource.SHEEP));
			wheatCards.add(new ResourceCard(Resource.WHEAT));
		}	
	}
}
