package client.points;

import client.base.*;
import client.data.PlayerInfo;
import model.Game;
import model.Player;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController, Observer {

	private IGameFinishedView finishedView;
	private int currentPoints;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		
		super(view);
		
		setFinishedView(finishedView);
		this.currentPoints = 0;
		initFromModel();

		// This Controller will now be notified to any changes in the Game Object
		Game.getInstance().addObserver(this);
	}
	
	public IPointsView getPointsView() {
		
		return (IPointsView)super.getView();
	}

	
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	private void initFromModel() {
		//every player starts with 0 points
		this.currentPoints = 0;
		getPointsView().setPoints(currentPoints);
	}

	/**
	 * This method is called whenever the observed object is changed. An application calls an
	 * Observable object's notifyObservers method to have all the object's observers notified of the change.
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
//		if(arg.equals("reset")){
//			this.currentPoints = 0;
//			getPointsView().setPoints(currentPoints);
//			return;
//		}

		int comparePoints = Game.getInstance().getPlayer().getVictoryPoints();

		//compare the points to see if the player has gained any more points
		if(comparePoints != this.currentPoints){
			this.currentPoints = comparePoints;
			getPointsView().setPoints(this.currentPoints);
		}

		//did anyone win?

		//index of this client's player
		int currentIndex = Game.getInstance().getPlayer().getPlayerInfo().getPlayerIndex();

		//made a list to make for loop easier to read
		List<Player> playersList = Game.getInstance().getPlayersList();

		//this for loop goes through all of the players and sees if anyone has reached 10 or more points
		//if a player is found with 10 points or more, view is switched to FinishedView()
		for(int i = 0; i < Game.getInstance().getPlayersList().size(); i++){
			Player currentPlayer = playersList.get(i);
			if(Game.getInstance().getPlayersList().get(i).getVictoryPoints() >= 10){
				//END THE GAME, SOMEONE HAS REACHED 10 OR MORE POINTS
				getFinishedView().setWinner(currentPlayer.getPlayerInfo().getName(), currentPlayer.getPlayerInfo().getPlayerIndex() == currentIndex);
				getFinishedView().showModal();
			}

		}



	}
	
}

