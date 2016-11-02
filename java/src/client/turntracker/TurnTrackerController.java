package client.turntracker;

import client.data.PlayerInfo;
import client.map.MapController;
import client.roll.RollController;
import client.states.DiscardingState;
import client.states.FirstRoundState;
import client.states.NotMyTurnState;
import client.states.PlayingState;
import model.Facade;
import model.Game;
import model.Player;
import server.ServerProxy;
import shared.definitions.CatanColor;
import client.base.*;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, Observer {

	private int currentTurn;
	private int largestArmy;
	private int longestRoad;
	private int winner;
	private boolean gameOver = false;
	private boolean playersInitialized = false;

	private String endTurnResponse;

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);
		
		//initFromModel();

		// This Controller will now be notified to any changes in the Game Object
		Game.getInstance().addObserver(this);
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {
		//end turn here... need to update the model with this response
		endTurnResponse = ServerProxy.getServer().finishTurn(Game.getInstance().getCurrentPlayerInfo().getPlayerIndex());
		//MapController.setState(new NotMyTurnState());
		RollController.setRolled(false);
	}
	
	private void initFromModel() {

		//default color of the catan client... white
		CatanColor playerColor = CatanColor.WHITE;

		//change the default color to the current player when color is chosen
		if(Game.getInstance().getCurrentPlayerInfo() != null){
			playerColor = Game.getInstance().getCurrentPlayerInfo().getColor();

		}

		//this is the method that sets the color
		getView().setLocalPlayerColor(playerColor);



		String gameState = MapController.getState().toString();

		switch (gameState) {
			case "FirstRoundState":
				getView().updateGameState("First Round", false);
				break;
			case "SecondRoundState":
				getView().updateGameState("Second Round", false);
				break;
			case "RollingState":
				getView().updateGameState("Rolling", false);
				break;
			case "RobbingState":
				getView().updateGameState("Robbing", false);
				break;
			case "PlayingState":
				getView().updateGameState("End Turn", true);
				break;
			case "NotMyTurnState":
				getView().updateGameState("Not My Turn", false);
				break;
			case "DiscardingState":
				getView().updateGameState("Discarding", false);
				break;
			default:
				getView().updateGameState("Not My Turn", false);
				break;
		}

	}

	/**
	 * This method is called whenever the observed object is changed. An application calls an
	 * Observable object's notifyObservers method to have all the object's observers notified of the change.
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(MapController.isGameStarted() && (Game.getInstance().getGameInfo().getPlayers().size() == 4)){
			initFromModel();
			currentTurn = Game.getInstance().getTurnTracker().getCurrentTurn();
			largestArmy = Game.getInstance().getTurnTracker().getLargestArmy();
			longestRoad = Game.getInstance().getTurnTracker().getLongestRoad();
			winner = -1;
			if(longestRoad == -1){
				System.out.println("LONGEST ROAD = NEGATIVE ONE");
			}
			if(largestArmy == -1){
				System.out.print("LARGEST ARMY = NEGATIVE ONE");
			}

			List<Player> playersList = Game.getInstance().getPlayersList();
			for(Player player : playersList){
				int index = player.getPlayerInfo().getPlayerIndex();
				if (!playersInitialized) {
					getView().initializePlayer(index, player.getPlayerInfo().getName(), player.getPlayerInfo().getColor());
				}
				//set the winner to whoever has 10+ victory points...
				if(player.getVictoryPoints() >= 10){
					//set this winner = Game.getInstance().getWinner();...????
					winner = player.getPlayerInfo().getPlayerIndex();
					gameOver = true;
				}

				//update the player information here
				getView().updatePlayer(index, player.getVictoryPoints(), isCurrentTurn(index), isLargestArmy(index), isLongestRoad(index));
//				getView().updatePlayer(index, player.getVictoryPoints(), isCurrentTurn(index), true, true);
			}
			playersInitialized = true;

			//check to see if the game is over...
			if(isGameOver()){
				//end the game here....
				//need to reset the game here
				getView().updateGameState("GAME OVER!", false);
			}
		}
		//game hasn't started yet
		else {
			getView().updateGameState("Waiting for other players...", false);
		}
	}

	private boolean isCurrentTurn(int index){
		if(index == currentTurn){
			return true;
		}
		else {
			return false;
		}
	}
	private boolean isLargestArmy(int index){
		if(index == largestArmy){
			return true;
		}
		else {
			return false;
		}
	}
	private boolean isLongestRoad(int index){
		if(index == longestRoad){
			return true;
		}
		else {
			return false;
		}
	}

	private boolean isGameOver(){
		return this.gameOver;
	}
}

