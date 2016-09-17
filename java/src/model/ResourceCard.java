package model;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class ResourceCard {
	protected enum Resource {
		ORE, BRICK, WOOD, SHEEP, WHEAT
	}
	
	private Resource type;
	
	public ResourceCard(Resource type) {
		this.type = type;
	}
	
	public Resource getType() {
		return type;
	}
}
