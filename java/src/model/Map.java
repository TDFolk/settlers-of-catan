package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the components of a map object for the specific game
 * Created by kcwillmore on 9/17/16.
 */
public class Map {
	/**
	 * the grid of hexes, the topmost hex is (0,0), each hex stored according to its hex location
	 * if each is (x,y), increasing x values indicate moving to the lower-right, increasing y moving to lower-left
	 */
	private Hex[][] hexes;
	private List<Settlement> settlements;
	private List<City> cities;
	private List<Road> roads;
	private List<Port> ports;
	//todo Map<VertexLocation, Building>?
	//Map<EdgeLocation, Road>?

	/**
	 * Constructor of a map
	 * Initializes the dice number tokens that go on the hexes
	 * Creates hexes, puts number tokens on them. Also creates the ocean hexes
	 *
	 * @param radius  The radius of the map (it includes the center hex, and the ocean hexes)
	 */
	public Map(int radius) {
		int[] numberTokens = {2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12};


	}
	
	
	
	
	
	
	
	
}
