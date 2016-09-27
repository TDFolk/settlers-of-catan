package model;

import java.util.List;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class TurnTracker {
	/**
	 * The turn order of the players in the game
	 */
	private List<Player> playerOrder;
	private int turn;
	
	/**
	 * Constructs a new TurnTracker with the given list of players
	 * @param players The players at the start of the game
	 */
	public TurnTracker(List<Player> players) {
		
	}
	
	
	/**
	 * Adds a player to the turn order
	 * @param player the player to add
	 */
	public void addPlayer(Player player) {
		
	}
	
	/**
	 * Removes a player from the gameplay and from the turn order
	 * @param player The player to remove from the game
	 */
	public void removePlayer(Player player) {
		
	}
	
	/**
	 * Ends the current player's turn and moves to the next player's turn
	 * @return The next player whose turn it is
	 */
	public Player nextPlayerTurn() {
		turn += 1;
		turn %= playerOrder.size();
		return playerOrder.get(turn);
	}
	
	/**
	 * Specifies which player's turn it is
	 * @return The player whose turn it is
	 */
	public Player getPlayerTurn() {
		return playerOrder.get(turn);
	}
	
	
	
}
