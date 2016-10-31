package client.roll;

import client.base.*;
import client.map.MapController;
import client.states.IGameState;
import client.states.RollingState;
import model.Game;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController, Observer {

	private IRollResultView resultView;
	private Random rng;

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
		
		setResultView(resultView);

		// This Controller will now be notified to any changes in the Game Object
		Game.getInstance().addObserver(this);

		rng = new Random();
	}
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView)getView();
	}
	
	@Override
	public void rollDice() {
		//generate two dice rolls between 1 and 6
		int dice1 = rng.nextInt(6) + 1;
		int dice2 = rng.nextInt(6) + 1;

		resultView.setRollValue(dice1 + dice2);
		getRollView().closeModal();
		getResultView().showModal();
	}

	/**
	 * This method is called whenever the observed object is changed. An application calls an
	 * Observable object's notifyObservers method to have all the object's observers notified of the change.
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(MapController.getState() instanceof RollingState){
			if(Game.getInstance().getTurnTracker().getCurrentTurn() == Game.getInstance().getCurrentPlayerInfo().getPlayerIndex()){
				getRollView().showModal();
			}
		}
	}

}

