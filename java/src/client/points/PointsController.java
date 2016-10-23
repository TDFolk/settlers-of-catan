package client.points;

import client.base.*;
import model.Game;

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
		if(arg.equals("reset")){
			this.currentPoints = 0;
			getPointsView().setPoints(currentPoints);
			return;
		}

		int index = Game.getInstance().getPlayer().getPlayerIndex();
		int comparePoints = Game.getInstance().getPlayer().getVictoryPoints();

		if(comparePoints != this.currentPoints){
			
		}


	}
	
}

