package client.join;

import client.base.*;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.misc.MessageView;
import model.Game;
import server.ServerProxy;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController, Observer {

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);

		// This Controller will now be notified to any changes in the Game Object
		Game.getInstance().addObserver(this);
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}


	/**
	 * Displays the player waiting view
	 */
	@Override
	public void start() {
		ArrayList<PlayerInfo> playerList = new ArrayList<>(Game.getInstance().getGameInfo().getPlayers());
		PlayerInfo[] gamePlayerInfo = playerList.toArray(new PlayerInfo[playerList.size()]);
		getView().setPlayers(gamePlayerInfo);

		if (gamePlayerInfo.length >= 4) {

			getView().closeModal();
		}
		else {
			getView().showModal();
		}
	}

	/**
	 * Called when the "Add AI" button is clicked in the player waiting view
	 */
	@Override
	public void addAI() {
		String newAI = getView().getSelectedAI();
		ServerProxy.getServer().gameAddAI(newAI);
		String[] listAI = new String[10];
		listAI[0] = ServerProxy.getServer().gameListAI();
		getView().setAIChoices(listAI);
	}

	/**
	 * This method is called whenever the observed object is changed. An application calls an
	 * Observable object's notifyObservers method to have all the object's observers notified of the change.
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
//		for (int i = 0; i < gamePlayerInfo.length; i++) {
//			gamePlayerInfo[i].setColor(Game.getInstance().getPlayersList().get(i).getPlayerInfo().getColor());
//			gamePlayerInfo[i].setId(Game.getInstance().getPlayersList().get(i).getPlayerInfo().getId());
//			gamePlayerInfo[i].setName(Game.getInstance().getPlayersList().get(i).getPlayerInfo().getName());
//			gamePlayerInfo[i].setPlayerIndex(Game.getInstance().getPlayersList().get(i).getPlayerInfo().getPlayerIndex());
//		}
		// Could you do getView().setPlayers(Game.getInstance().getPlayersList().toArray(gamePlayerInfo)); ?


		if (arg.equals("reset")) {
			return;
		}



		if (getView().isModalShowing()) {
			PlayerInfo[] gamePlayerInfo = new PlayerInfo[Game.getInstance().getPlayersList().size()];
			Game.getInstance().getPlayersList().toArray(gamePlayerInfo);
			getView().closeModal();
			if (gamePlayerInfo.length < 4) {
				getView().setPlayers(Game.getInstance().getPlayersList().toArray(gamePlayerInfo));
				getView().showModal();
			}
		}
	}

//	private boolean isFull(){
//		if(Game.getInstance().getGameInfo().getPlayers().size() >= 4){
//			return true;
//		}
//		return false;
//	}
//
//	private void updatePlayerList(){
//		ArrayList<PlayerInfo> playerList = new ArrayList<>(Game.getInstance().getGameInfo().getPlayers());
//		PlayerInfo[] players = playerList.toArray(new PlayerInfo[playerList.size()]);
//		getView().setPlayers(players);
//	}
}

