package model;

/**
 * Represents the object of a specified resource card
 * Created by kcwillmore on 9/17/16.
 */
public class ResourceCard {
	protected enum Resource {
		BRICK, ORE, SHEEP, WHEAT, WOOD
	}
	
	private Resource type;

	/**
	 * Resource Card constructor, makes a card with one of the five resource types
	 * used for paying costs and stuff
	 *
	 * @param type the resource this card will represent (brick/ore/sheep/wheat/wood)
	 */
	public ResourceCard(Resource type) {
		this.type = type;
	}
	
	public Resource getType() {
		return type;
	}

}
