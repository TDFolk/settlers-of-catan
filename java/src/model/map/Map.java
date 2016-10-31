package model.map;

import client.main.Catan;
import model.Game;
import model.Player;
import model.pieces.Building;
import model.pieces.Road;
import model.pieces.Settlement;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.locations.*;

import java.util.ArrayList;
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
	private HexLocation robber;

	/**
	 * Constructor of a map
	 * all the info is pre-made by the database and packaged here for y(our) convenience
	 *
	 * @param hexes that make up the map
	 * @param buildings on the map
	 * @param roads on the map
	 * @param ports on the map
	 */
	public Map(List<Hex> hexes, List<Building> buildings, List<Road> roads, List<Port> ports, int radius, HexLocation robber) {
		this.hexes = hexes;
		this.buildings = buildings;
		this.roads = roads;
		this.ports = ports;
		this.radius = radius;
		this.robber = robber;
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

	/**
	 * Finds out if a city or settlement is built on the vertices of this edge
	 *
	 * @return the building adjacent to this edge, null if none is found
	 */
	public Building buildingByEdge(EdgeLocation location) {
		Building building = getBuildingAtVertex(edgeToLeftVertex(location));
		if (building == null) {
			building = getBuildingAtVertex(edgeToRightVertex(location));
		}
		return building;
	}

	/**
	 * Determines if a road can be placed at the specified location by checking for
	 * 		an adjacent road or building of the same color,
	 * 		and checking if the road is being placed on the other side of a building of another color
	 * @param location the edge location to place the road
	 * @return true if a road can be placed at the specified location
	 */
	public boolean canPlaceRoad(EdgeLocation location, Player player) {
		//already a road at that location, can't build
		if (getRoadAtEdge(location) != null) {
			return false;
		}
		// Tries to place a road into the ocean water
		if (isWater(location)) {
			// Can't build into the ocean!
			return false;
		}




		//check for buildings adjacent to road edge
		Building adjacentBuilding = buildingByEdge(location);
		if (Game.getInstance().getTurnTracker().getStatus().equals("SecondRound")) {
			if (adjacentBuilding != null && buildings != null) {
				// Always going to be at either index 0 or 7 i believe, but it is random which one
				for (int i = 0; i < 4; i++) {
					if (adjacentBuilding.getLocation().getNormalizedLocation().equals(Game.getInstance().getMap().buildings.get(i).getLocation().getNormalizedLocation())) {
						return false;
					}
				}
			}
		}
		if (adjacentBuilding != null) {
			//if the same colored building, all is good
			if (adjacentBuilding.getColor().equals(player.getPlayerInfo().getColor())) {
				return true;
			}

			//if different colored building, must have a road of same color on the opposite end
			if (adjacentBuilding.getLocation().getNormalizedLocation().equals(edgeToLeftVertex(location).getNormalizedLocation())) { //leftside
				//check both edges to the right of this, if a road is on one of them and it is the same color, all is well
				return roadsToTheRight(location, player.getPlayerInfo().getColor());
			}
			else { //rightside
				//same as for the leftside, but checking the opposite edges for friendly roads
				return roadsToTheLeft(location, player.getPlayerInfo().getColor());
			}
		}
		//if no settlement, must have road of same color in any direction
		if (!Game.getInstance().getTurnTracker().getStatus().equals("SecondRound")) {
			return roadsToTheLeft(location, player.getPlayerInfo().getColor()) || roadsToTheRight(location, player.getPlayerInfo().getColor());
		}
		return false;
	}

	public boolean isWater(EdgeLocation edgeLocation) {
		if (edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(3, 0), EdgeDirection.North)) || // GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(2, 1), EdgeDirection.NorthEast)) || // GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(1, 2), EdgeDirection.NorthEast)) || // GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.NorthEast)) || // GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.NorthWest)) || // GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(-1, 3), EdgeDirection.NorthWest)) || // GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(-2, 3), EdgeDirection.NorthWest)) || // GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(-3, 3), EdgeDirection.North)) || // GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(-3, 2), EdgeDirection.North)) || // GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(-3, 1), EdgeDirection.North)) || // GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.NorthEast)) || // GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(-2, -1), EdgeDirection.NorthEast)) || // GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(-1, -2), EdgeDirection.NorthEast)) || //GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(1, -3), EdgeDirection.NorthWest)) || //GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(2, -3), EdgeDirection.NorthWest)) || // GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.NorthWest)) || // GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(3, -2), EdgeDirection.North)) || // GOOD
				edgeLocation.getNormalizedLocation().equals(new EdgeLocation(new HexLocation(3, -1), EdgeDirection.North)))  { // GOOD
			return true;
		}

		return false;
	}

	/**
	 * Checks if the location is a valid vertex for the settlement to be placed.
	 * @param location the location to place the settlement
	 * @return true if the location is a valid vertex for placement
	 */
	public boolean canPlaceSettlement(VertexLocation location, Player player) {
		//get the edges surrounding the settlement, once must have a road of same color and none may have a settlement on the other end
		//edge1 is easy to find, but may be on either the right or the left
		EdgeLocation adjacentEdge1 = new EdgeLocation(location.getNormalizedLocation().getHexLoc(), EdgeDirection.North);
		EdgeLocation adjacentEdge2;
		EdgeLocation adjacentEdge3;
		//edge1 is to the right of the settlement >-
		if (edgeToLeftVertex(adjacentEdge1).getNormalizedLocation().equals(location.getNormalizedLocation())) {
			adjacentEdge2 = edgeToLeftEdge(adjacentEdge1);
			adjacentEdge3 = edgeToLeftEdge(new EdgeLocation(adjacentEdge1.getHexLoc().getNeighborLoc(EdgeDirection.North), EdgeDirection.South));
		}
		else {
			//edge1 is to the left of the settlement -<
			adjacentEdge2 = edgeToRightEdge(adjacentEdge1);
			adjacentEdge3 = edgeToRightEdge(new EdgeLocation(adjacentEdge1.getHexLoc().getNeighborLoc(EdgeDirection.North), EdgeDirection.South));
		}

		// The edges all around the water
		if (location.getHexLoc().getX() == 4 ||
				location.getHexLoc().getX() == -4 ||
				location.getHexLoc().getY() == 4 ||
				location.getHexLoc().getY() == -3 ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(3, 0), VertexDirection.NorthEast)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(3, -1), VertexDirection.NorthEast)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(3, -2), VertexDirection.NorthEast)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(-3, 1), VertexDirection.NorthWest)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(-3, 2), VertexDirection.NorthWest)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(-3, 3), VertexDirection.NorthWest)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(-3, 0), VertexDirection.NorthWest)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(-3, 0), VertexDirection.NorthEast)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(-2, -1), VertexDirection.NorthWest)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(-2, -1), VertexDirection.NorthEast)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(-1, -2), VertexDirection.NorthWest)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(-1, -2), VertexDirection.NorthEast)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(0, -3), VertexDirection.NorthWest)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(1, 3), VertexDirection.NorthEast)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(1, 3), VertexDirection.NorthWest)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(2, 2), VertexDirection.NorthEast)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(2, 2), VertexDirection.NorthWest)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(3, 1), VertexDirection.NorthEast)) ||
				location.getNormalizedLocation().equals(new VertexLocation(new HexLocation(3, 1), VertexDirection.NorthWest))) {
			return false;
		}

		//ensures that there are no settlements adjacent to your location
		if (buildingByEdge(adjacentEdge1) == null &&
				buildingByEdge(adjacentEdge2) == null &&
				buildingByEdge(adjacentEdge3) == null) {
			if (Game.getInstance().getTurnTracker().getStatus().equals("FirstRound") ||
					Game.getInstance().getTurnTracker().getStatus().equals("SecondRound")) {
				return true;
			}
			else {
				//ensures that there is a road that belongs to you at at least one of the three directions
				if (getRoadAtEdge(adjacentEdge1) != null && getRoadAtEdge(adjacentEdge1).getColor() == player.getPlayerInfo().getColor()
						|| getRoadAtEdge(adjacentEdge2) != null && getRoadAtEdge(adjacentEdge2).getColor() == player.getPlayerInfo().getColor()
						|| getRoadAtEdge(adjacentEdge3) != null && getRoadAtEdge(adjacentEdge3).getColor() == player.getPlayerInfo().getColor()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Given an edge, finds the vertex to the left of it
	 * @param location the edge under consideration
	 * @return the vertex to the left of this edge
	 */
	private VertexLocation edgeToLeftVertex(EdgeLocation location) {
		switch (location.getDir()) {
			case NorthWest:
				return new VertexLocation(location.getHexLoc(), VertexDirection.West);
			case North:
				return new VertexLocation(location.getHexLoc(), VertexDirection.NorthWest);
			case NorthEast:
				return new VertexLocation(location.getHexLoc(), VertexDirection.NorthEast);
			case SouthWest:
				return new VertexLocation(location.getHexLoc(), VertexDirection.West);
			case South:
				return new VertexLocation(location.getHexLoc(), VertexDirection.SouthWest);
			case SouthEast:
				return new VertexLocation(location.getHexLoc(), VertexDirection.SouthEast);
			default:
				assert false;
				return null;
		}
	}

	/**
	 * Given  an edge, finds the vertex to the right of it
	 * @param location the edge under consideration
	 * @return the vertex tot he right of this edge
	 */
	private VertexLocation edgeToRightVertex(EdgeLocation location) {
		switch (location.getDir()) {
			case NorthWest:
				return new VertexLocation(location.getHexLoc(), VertexDirection.NorthWest);
			case North:
				return new VertexLocation(location.getHexLoc(), VertexDirection.NorthEast);
			case NorthEast:
				return new VertexLocation(location.getHexLoc(), VertexDirection.East);
			case SouthWest:
				return new VertexLocation(location.getHexLoc(), VertexDirection.SouthWest);
			case South:
				return new VertexLocation(location.getHexLoc(), VertexDirection.SouthEast);
			case SouthEast:
				return new VertexLocation(location.getHexLoc(), VertexDirection.East);
			default:
				assert false;
				return null;
		}
	}

	/**
	 * Finds the edge to the left of this edge (on this EdgeLocation's respective hex)
	 * @param edge the edge being keyed off of
	 * @return the edge to the left of this around the hex
	 */
	private EdgeLocation edgeToLeftEdge(EdgeLocation edge) {
		switch (edge.getDir()) {
			case NorthWest:
				return new EdgeLocation(edge.getHexLoc(), EdgeDirection.SouthWest);
			case North:
				return new EdgeLocation(edge.getHexLoc(), EdgeDirection.NorthWest);
			case NorthEast:
				return new EdgeLocation(edge.getHexLoc(), EdgeDirection.North);
			case SouthWest:
				return new EdgeLocation(edge.getHexLoc(), EdgeDirection.NorthWest);
			case South:
				return new EdgeLocation(edge.getHexLoc(), EdgeDirection.SouthWest);
			case SouthEast:
				return new EdgeLocation(edge.getHexLoc(), EdgeDirection.South);
			default:
				assert false;
				return null;
		}
	}

	/**
	 * Finds the edge to the right of this edge (on this EdgeLocation's respective hex)
	 * @param edge the edge being keyed off of
	 * @return the edge to the right of this around the hex
	 */
	private EdgeLocation edgeToRightEdge(EdgeLocation edge) {
		switch (edge.getDir()) {
			case NorthWest:
				return new EdgeLocation(edge.getHexLoc(), EdgeDirection.North);
			case North:
				return new EdgeLocation(edge.getHexLoc(), EdgeDirection.NorthEast);
			case NorthEast:
				return new EdgeLocation(edge.getHexLoc(), EdgeDirection.SouthEast);
			case SouthWest:
				return new EdgeLocation(edge.getHexLoc(), EdgeDirection.South);
			case South:
				return new EdgeLocation(edge.getHexLoc(), EdgeDirection.SouthEast);
			case SouthEast:
				return new EdgeLocation(edge.getHexLoc(), EdgeDirection.NorthEast);
			default:
				assert false;
				return null;
		}
	}

	/**
	 * Checks if there are any roads of the same color to the right of this edge
	 * because nothing is OK anymore
	 * @param edge the edge you're probably trying to put a road on
	 * @param playerColor the color of the player probably trying to place a road
	 * @return true if there are roads of the same color to the right of this edge
	 */
	private boolean roadsToTheRight(EdgeLocation edge, CatanColor playerColor) {
		//find the edge's location according to the other hex is is a side of
		EdgeLocation reflection = new EdgeLocation(edge.getHexLoc().getNeighborLoc(edge.getDir()), edge.getDir().getOppositeDirection());

		return (getRoadAtEdge(edgeToRightEdge(edge)) != null
				&& getRoadAtEdge(edgeToRightEdge(edge)).getColor().equals(playerColor)
				|| getRoadAtEdge(edgeToRightEdge(reflection)) != null
				&& getRoadAtEdge(edgeToRightEdge(reflection)).getColor().equals(playerColor));
	}

	private boolean roadsToTheLeft(EdgeLocation edge, CatanColor playerColor) {
		//find the edge's location according to the other hex is is a side of
		EdgeLocation reflection = new EdgeLocation(edge.getHexLoc().getNeighborLoc(edge.getDir()), edge.getDir().getOppositeDirection());

		return (getRoadAtEdge(edgeToLeftEdge(edge)) != null
				&& getRoadAtEdge(edgeToLeftEdge(edge)).getColor().equals(playerColor)
				|| getRoadAtEdge(edgeToLeftEdge(reflection)) != null
				&& getRoadAtEdge(edgeToLeftEdge(reflection)).getColor().equals(playerColor));
	}


	public List<Hex> getHexes() {
		return hexes;
	}

	public void setHexes(List<Hex> hexes) {
		this.hexes = hexes;
	}

	public List<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

	public List<Road> getRoads() {
		return roads;
	}

	public void setRoads(List<Road> roads) {
		this.roads = roads;
	}

	public List<Port> getPorts() {
		return ports;
	}

	public void setPorts(List<Port> ports) {
		this.ports = ports;
	}

	public HexLocation getRobber() {
		return robber;
	}

	public void setRobber(HexLocation robber) {
		this.robber = robber;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
}