package model;

import exception.PlayerIDException;

public class PlayerID {	
	private int id;
	
	/**
	 * Initializes a player's identifier
	 * @pre the id must be in [0, 3]
	 * @param id the player's identifier
	 * @throws PlayerIDException if the id specified is outside the domain [0, 3]
	 */
	public PlayerID(int id) throws PlayerIDException {
		
	}
		
	public int getID() {
		return id;
	}
}
