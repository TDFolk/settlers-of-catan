package client.join;

import client.base.*;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.map.MapController;
import client.misc.MessageView;
import client.states.FirstRoundState;
import model.Facade;
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

		String[] AIList = new String[1];
		//AIList[0] = ServerProxy.getServer().gameListAI();
		AIList[0] = "LARGEST_ARMY";
		getView().setAIChoices(AIList);
	}

	/**
	 * Called when the "Add AI" button is clicked in the player waiting view
	 */
	@Override
	public void addAI() {
		String newAI = getView().getSelectedAI();
		ServerProxy.getServer().gameAddAI(newAI);
	}


	/**
	 * This method is called whenever the observed object is changed. An application calls an
	 * Observable object's notifyObservers method to have all the object's observers notified of the change.
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {

		if (getView().isModalShowing()) {
			getView().closeModal();
			ArrayList<PlayerInfo> playerList = new ArrayList<>(Game.getInstance().getGameInfo().getPlayers());
			PlayerInfo[] gamePlayerInfo = playerList.toArray(new PlayerInfo[playerList.size()]);
			getView().setPlayers(gamePlayerInfo);

			if (gamePlayerInfo.length == 4) {

				//change the state
				//MapController.setState(new FirstRoundState());
				//game is starting now!!!!
				//MapController.setGameStarted(true);
				if (Game.getInstance().getPlayer().getPlayerInfo().getPlayerIndex() == 3) {
					String currentModel = ServerProxy.getServer().gameModelVersion(Facade.getInstance().getVersionNumber());
					if (currentModel != null) {
						if (!currentModel.equals("\"true\"")) {
							System.out.println("Updating Model");
							Facade.getInstance().replaceModel(currentModel);
							//Facade.getInstance().incrementVersionNumber();
						}
					}
				}
				getView().closeModal();
			}
			else {
				getView().showModal();
			}
		}
	}

}

