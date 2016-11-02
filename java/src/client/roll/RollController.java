package client.roll;

import client.base.*;
import client.map.MapController;
import client.states.IGameState;
import client.states.PlayingState;
import client.states.RobbingState;
import client.states.RollingState;
import model.Facade;
import model.Game;
import server.ServerProxy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;



/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController, Observer {

	private IRollResultView resultView;
	private Timer countdownTimer = new Timer(0, null);
	private static boolean rolled = false;

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
		//stop countdown here
		if(countdownTimer.isRunning()){
			countdownTimer.stop();
		}

		int dice1 = (int)(Math.random() * 6) + 1;
		int dice2 = (int)(Math.random() * 6) + 1;
		int totalValue = dice1 + dice2;
		
		/*
		if(totalValue == 7){
			MapController.setState(new RobbingState());
		}
		*/

		getRollView().closeModal();
		//getRollView().setMessage("Rolling automatically in 5");
		//making the call to the server to roll
		String response = ServerProxy.getServer().rollNumber(Game.getInstance().getCurrentPlayerInfo().getPlayerIndex(), totalValue);
		
		if (totalValue == 7)
			System.out.println("SERVER MODEL WHEN ROLLING A 7: " + response);

		getResultView().setRollValue(totalValue);
		getResultView().showModal();
	}
	
	public static boolean hasRolled() {
		return rolled;
	}

	public static void setRolled(boolean rolled) {
		RollController.rolled = rolled;
	}
	

	/**
	 * This method is called whenever the observed object is changed. An application calls an
	 * Observable object's notifyObservers method to have all the object's observers notified of the change.
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		//check if the state is in rolling state
		if(MapController.getState() instanceof RollingState && !rolled){
			//check if the servers's turn matches this client's turn
			if(Game.getInstance().getTurnTracker().getCurrentTurn() == Game.getInstance().getCurrentPlayerInfo().getPlayerIndex()){
				//if so, show the modal and start the countdown
				getRollView().setMessage("Rolling automatically in 5");
				getRollView().showModal();
				rolled = true;
				countdown();
			}
		}
	}

	private void countdown(){
		ActionListener actionListener = new ActionListener() {
			int seconds = 5;
			@Override
			public void actionPerformed(ActionEvent e) {
				seconds--;
				getRollView().setMessage("Rolling automatically in " + seconds);

				//if the seconds goes down less than 5
				if(seconds <= 0){
					getRollView().setMessage("Rolling automatically in " + seconds);
					countdownTimer.stop();
					rollDice();
					//MapController.setState(new PlayingState());
				}
			}
		};
		countdownTimer = new Timer(1000, actionListener);
		countdownTimer.start();
	}
}

