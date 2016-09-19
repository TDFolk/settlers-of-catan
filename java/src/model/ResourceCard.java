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
	
	public ResourceCard(Resource type) {
		this.type = type;
	}
	
	public Resource getType() {
		return type;
	}

}
