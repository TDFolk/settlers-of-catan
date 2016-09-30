package model.pieces;

import shared.definitions.CatanColor;
import shared.locations.VertexLocation;

/**
 * Building is a parent class for cities and settlements
 */
public abstract class Building {

	private CatanColor color;
	private VertexLocation location;

	/**
	 * Building constructor, requires a place to go and a player/color associated
	 *
	 * @param color the color of the player who is building the Building
	 * @param location the vertex location where the building is to go
	 */
	public Building(CatanColor color, VertexLocation location) {
		this.color = color;
		this.location = location;
	}

	public CatanColor getColor() {
		return color;
	}

	public VertexLocation getLocation() {
		return location;
	}
}
