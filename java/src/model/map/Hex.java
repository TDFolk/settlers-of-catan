package model.map;

import shared.definitions.ResourceType;
import shared.locations.HexLocation;

/**
 * Represents a hex tile object used to build the map of the game
 * Created by kcwillmore on 9/17/16.
 */
public class Hex {
	private ResourceType resource;
	private HexLocation location;
	private int numberToken;
	//boolean robbed?

	/**
	 * Constructor of hex tile
	 * @param resource resource represented by the hex
	 * @param location location of the hex on the map
	 * @param numberToken number that if rolled rewards the given resource
	 */
	public Hex(ResourceType resource, HexLocation location, int numberToken) {
		this.resource = resource;
		this.location = location;
		this.numberToken = numberToken;
	}
	
	public ResourceType getResourceType() {
		return resource;
	}
	
	public HexLocation getLocation() {
		return location;
	}
	
	public int getNumberToken() {
		return numberToken;
	}
	
	
}
