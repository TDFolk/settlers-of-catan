package model.map;

import model.pieces.Building;
import model.pieces.Road;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

import java.util.List;

/**
 * Represents the components of a map object for the specific game
 * Created by kcwillmore on 9/17/16.
 */
public class Map {
	private List<Hex> hexes;
	private List<Building> buildings;
	private List<Road> roads;
	private List<Port> ports;
	private int radius;

	/**
	 * Constructor of a map
	 * Initializes the dice number tokens that go on the hexes
	 * Creates hexes, puts number tokens on them. Also creates the ocean hexes
	 *
	 * @param radius  The radius of the map (it includes the center hex, and the ocean hexes)
	 */
	public Map(int radius) {
		int[] numberTokens = {2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12};

		this.radius = radius;
	}

	/**
	 * Get the city or settlement at a location if one exists
	 * @param location the vertex under scrutiny
	 * @return the building at that vertex, null if none exists
	 */
	public Building getBuildingAtVertex(VertexLocation location) {
		for (Building building : buildings) {
			if (building.getLocation().getNormalizedLocation().equals(location.getNormalizedLocation())) {
				return building;
			}
		}
		return null;
	}

	public Road getRoadAtEdge(EdgeLocation location) {
		for (Road road: roads) {
			if (road.getLocation().getNormalizedLocation().equals(location.getNormalizedLocation())) {
				return road;
			}
		}
		return null;
	}
}
