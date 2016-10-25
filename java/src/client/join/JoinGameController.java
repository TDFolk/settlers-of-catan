package client.join;

import command.game.GameCreateObject;
import command.game.GameCreateObjectResult;
import command.game.GameListObject;
import model.Game;
import shared.definitions.CatanColor;
import client.base.*;
import client.data.*;
import client.misc.*;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController, Observer {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;

	private GameInfo game;


	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);

		Game.getInstance().addObserver(this);
	}
	
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}




	/**
	 * Displays the join game view
	 */
	@Override
	public void start() {
		GameInfo[] gameInfos = Game.getInstance().getServer().gameList().getGameInfos();

		if(gameInfos != null){
			getJoinGameView().setGames(gameInfos, );
		}


		if(!getJoinGameView().isModalShowing()) {
			getJoinGameView().showModal();
		}
		//startTimer(); ??
	}


	/**
	 * Called by the join game view when the user clicks "Create new game"
	 * button. Displays the new game view.
	 */
	@Override
	public void startCreateNewGame() {


		getNewGameView().showModal();
	}


	/**
	 * Called by the new game view when the user clicks the "Cancel" button
	 */
	@Override
	public void cancelCreateNewGame() {
		getNewGameView().closeModal();
	}


	/**
	 * Called by the new game view when the user clicks the "Create Game" button
	 */
	@Override
	public void createNewGame() {
		String title = getNewGameView().getTitle();

		if (title.equals("")) {
			getMessageView().setTitle("Title Error");
			getMessageView().setMessage("Your game needs a title");
			getMessageView().showModal();
			return;
		}
		boolean randomHexes = getNewGameView().getRandomlyPlaceHexes();
		boolean randomNumbers = getNewGameView().getRandomlyPlaceNumbers();
		boolean randomPorts = getNewGameView().getUseRandomPorts();

		GameCreateObjectResult myNewGame = Game.getServer().gameCreate(randomHexes, randomNumbers, randomPorts, title);



		getNewGameView().closeModal();
	}


	/**
	 * Called by the join game view when the user clicks a "Join" or "Re-join"
	 * button. Displays the select color view.
	 *
	 * @param game
	 *            The game that the user is joining
	 */
	@Override
	public void startJoinGame(GameInfo game) {

		//getSelectColorView().

		getSelectColorView().showModal();
	}



	/**
	 * Called by the select color view when the user clicks the "Cancel" button
	 */
	@Override
	public void cancelJoinGame() {
	
		getJoinGameView().closeModal();
	}


	/**
	 * Called by the select color view when the user clicks the "Join Game"
	 * button
	 *
	 * @param color
	 *            The color selected by the user
	 */
	@Override
	public void joinGame(CatanColor color) {

		//Game.getServer().gameJoin(,color);

		// If join succeeded
		getSelectColorView().closeModal();
		getJoinGameView().closeModal();
		joinAction.execute();


	}

	/**
	 * This method is called whenever the observed object is changed. An
	 * application calls an <tt>Observable</tt> object's
	 * <code>notifyObservers</code> method to have all the object's
	 * observers notified of the change.
	 *
	 * @param o   the observable object.
	 * @param arg an argument passed to the <code>notifyObservers</code>
	 */
	@Override
	public void update(Observable o, Object arg) {

	}
}

