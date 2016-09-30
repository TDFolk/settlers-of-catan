package model;

import shared.definitions.ResourceType;

/**
 * Represents the object of a specified resource card
 * Created by kcwillmore on 9/17/16.
 */
public class ResourceCard {
//	protected enum Resource {
//		BRICK, ORE, SHEEP, WHEAT, WOOD
//	}
	// Let's use the ResourceType enum given in shared.definitions
	
	private ResourceType type;

	/**
	 * Resource Card constructor, makes a card with one of the five resource types
	 * used for paying costs and stuff
	 *
	 * @param type the resource this card will represent (brick/ore/sheep/wheat/wood)
	 */
	public ResourceCard(ResourceType type) {
		this.type = type;
	}
	
	public ResourceType getType() {
		return type;
	}

}
