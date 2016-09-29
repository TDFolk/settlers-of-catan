package model;

import java.util.List;

/**
 * Represents the components of a map object for the specific game
 * Created by kcwillmore on 9/17/16.
 */
public class Map {
	private List<Hex> hexes;
	private List<Settlement> settlements;
	private List<City> cities;
	private List<Road> roads;
	private List<Port> ports;
	//todo Map<VertexLocation, Building>?
	//Map<EdgeLocation, Road>?

	/**
	 * Constructor of a map
	 * Initializes the dice number tokens that go on the hexes
	 */
	public Map() {
		int[] numberTokens = {2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12};
	}
	
	
	
	
	
	
	
	
}
