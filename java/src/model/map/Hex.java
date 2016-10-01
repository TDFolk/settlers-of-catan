package model.map;

import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;

/**
 * Represents a hex tile object used to build the map of the game
 * Created by kcwillmore on 9/17/16.
 */
public class Hex {
	private HexType type;
	private HexLocation location;
	private int numberToken;
	//boolean robbed?

	/**
	 * Constructor of hex tile
	 * @param type resource represented by the hex
	 * @param location location of the hex on the map
	 * @param numberToken number that if rolled rewards the given resource
	 */
	public Hex(HexType type, HexLocation location, int numberToken) {
		this.type = type;
		this.location = location;
		this.numberToken = numberToken;
	}
	
	public HexType getHexType() {
		return type;
	}
	
	public HexLocation getLocation() {
		return location;
	}
	
	public int getNumberToken() {
		return numberToken;
	}
	
	
}
