package client.turntracker;

import client.data.PlayerInfo;
import client.map.MapController;
import client.states.DiscardingState;
import model.Facade;
import model.Game;
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

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);
		
		initFromModel();

		// This Controller will now be notified to any changes in the Game Object
		Game.getInstance().addObserver(this);
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {
		ServerProxy.getServer().finishTurn(Game.getInstance().getPlayer().getPlayerInfo().getPlayerIndex());
	}
	
	private void initFromModel() {
		//CatanColor playerColor = Game.getInstance().getPlayer().getPlayerInfo().getColor();
		CatanColor playerColor = CatanColor.BLUE;
		
		getView().setLocalPlayerColor(playerColor);

		PlayerInfo[] playersInfo = new PlayerInfo[Game.getInstance().getPlayersList().size()];
		for (int i = 0; i < playersInfo.length; i++) {
			playersInfo[i] = Game.getInstance().getPlayersList().get(i).getPlayerInfo();
		}

		if (playersInfo.length == 4) {
			for (int i = 0; i < playersInfo.length; i++) {
				getView().initializePlayer(playersInfo[i].getPlayerIndex(), playersInfo[i].getName(),
						playersInfo[i].getColor());
			}
		}

		String gameState = MapController.getState().toString();

		switch (gameState) {
			case "FirstRoundState":
				getView().updateGameState("First Round Phase", false);
				break;
			case "SecondRoundState":
				getView().updateGameState("Second Round State", false);
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
				getView().updateGameState("Relax and Take Notes", false);
				break;
			case "DiscardingState":
				getView().updateGameState("Discarding", false);
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
		initFromModel();
	}
}

