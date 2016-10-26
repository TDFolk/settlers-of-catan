package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class TurnTracker {
	/**
	 * The turn order of the players in the game
	 */
	private int currentTurn;
	private int longestRoad;
	private int largestArmy;
	private String status;

	/**
	 * Constructs a new TurnTracker with no players
	 */
	public TurnTracker() {
	}
	
	/**
	 * Constructs a new TurnTracker with the given data with the integers referring to the player index
	 *
	 */
	public TurnTracker(String status, int currnetTurn, int longestRoad, int largestArmy) {

		this.status = status;
		this.currentTurn = currnetTurn;
		this. longestRoad = longestRoad;
		this.largestArmy = largestArmy;

	}

	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	public int getLongestRoad() {
		return longestRoad;
	}

	public void setLongestRoad(int longestRoad) {
		this.longestRoad = longestRoad;
	}

	public int getLargestArmy() {
		return largestArmy;
	}

	public void setLargestArmy(int largestArmy) {
		this.largestArmy = largestArmy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
