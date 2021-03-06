package client.communication;

import client.base.*;
import com.sun.prism.impl.BaseMesh;
import model.Facade;
import model.Game;
import model.Player;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import server.ServerPoller;
import server.ServerProxy;
import shared.definitions.CatanColor;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController, Observer {

	public ChatController(IChatView view) {
		
		super(view);
		
		// This Controller will now be notified of any changes in the Game Object
		Game.getInstance().addObserver(this);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		int playerIndex = Game.getInstance().getPlayer().getPlayerInfo().getPlayerIndex();

		String model = ServerProxy.getServer().sendChat(playerIndex, Game.getInstance().getPlayer().getPlayerInfo().getName() + ": " + message);
		//String model = ServerProxy.getServer().sendChat(playerIndex, message);

		Facade.getInstance().replaceModel(model);

		getView().setEntries(Game.getInstance().getChatLog());
	}

	/**
	 * This method is called whenever the observed object is changed. An application calls an
	 * Observable object's notifyObservers method to have all the object's observers notified of the change.
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {

		getView().setEntries(Game.getInstance().getChatLog());
	}
}

