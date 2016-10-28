package client.communication;

import java.util.*;

import client.base.*;
import client.data.PlayerInfo;
import client.map.MapController;
import client.states.NotMyTurnState;
import model.Facade;
import model.Game;
import model.Player;
import shared.definitions.*;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController, Observer {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		
		initFromModel();

		// This Controller will now be notified of any changes in the Game Object
		Game.getInstance().addObserver(this);
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		Game game = Game.getInstance();
		List<LogEntry> log = game.getHistory();
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		entries.add(new LogEntry(CatanColor.BROWN, "THERE IS NO HISTORY"));
		getView().setEntries(entries);
	}

	/**
	 * This method is called whenever the observed object is changed. An application calls an
	 * Observable object's notifyObservers method to have all the object's observers notified of the change.
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {

		if (MapController.isGameStarted()) {


			Player player = Game.getInstance().getPlayersList().get(Game.getInstance().getPlayerTurn());
			PlayerInfo info = player.getPlayerInfo();
			String playerName = info.getName();
			CatanColor color = player.getPlayerInfo().getColor();

			String message = playerName + " has changed the value of Object: " + arg;
			Game.getInstance().addHistoryEntry(new LogEntry(color, message));

			if (arg != null) {
				System.out.println("GAME HISTORY: " + message);
			}


			getView().setEntries(Game.getInstance().getHistory());
		}
	}
	
}

