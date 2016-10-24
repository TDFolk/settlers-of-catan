package client.communication;

import client.base.*;
import model.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import shared.definitions.CatanColor;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController, Observer {
	private List<LogEntry> entries;

	public ChatController(IChatView view) {
		
		super(view);
		
		entries = new ArrayList<LogEntry>();
		// This Controller will now be notified of any changes in the Game Object
		Game.getInstance().addObserver(this);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		CatanColor color = Game.getInstance().getPlayer().getColor();
		entries.add(new LogEntry(color, message));
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
		
	}
}

