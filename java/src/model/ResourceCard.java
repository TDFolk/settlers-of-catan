package model;

/**
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

	public boolean canPickUp() {
		return false;
	}

	public boolean canUse() {
		return false;
	}
}
