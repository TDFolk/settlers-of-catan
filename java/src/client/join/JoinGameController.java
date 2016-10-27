package client.join;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import command.game.GameCreateObject;
import command.game.GameCreateObjectResult;
import command.game.GameListObject;
import model.Facade;
import model.Game;
import server.ServerPoller;
import server.ServerProxy;
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
	private PlayerInfo playerInfo;


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
		playerInfo = new PlayerInfo();
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

		getJoinGameView().showModal();
		GameInfo[] gameInfos = ServerProxy.getServer().gameList().getGameInfos();

		playerInfo.setName(ServerProxy.getServer().getCatanUsername());
		playerInfo.setId(Integer.parseInt(ServerProxy.getServer().getCatanPlayerID()));

		if(gameInfos != null){
			getJoinGameView().setGames(gameInfos, playerInfo);
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

		playerInfo.setPlayerIndex(0);

		GameCreateObjectResult myNewGame = ServerProxy.getServer().gameCreate(randomHexes, randomNumbers, randomPorts, title);
		if(myNewGame != null){
//			Gson gson = new Gson();
//			JsonElement gameID = gson.fromJson()
			getNewGameView().closeModal();
			//CHANGE RED LATER
			//ServerProxy.getServer().gameJoin(myNewGame.getId(), "red");
			this.start();
		}
		else {
			getMessageView().setTitle("Create New Game Error");
			getMessageView().setMessage("Unable to create new game.");
			getMessageView().showModal();
		}
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

		//set the game instance to the game chosen
		Game.getInstance().setGameInfo(game);
		this.game = game;

	//	Facade.getInstance().initializeData();

		for(int i = 0; i < CatanColor.values().length; i++) {

			for (PlayerInfo playerInfo : game.getPlayers()) {
				if ((CatanColor.values()[i] == (playerInfo.getColor()))) {

					//disable the colors that are chosen

					if(playerInfo.getId() != this.playerInfo.getId()){
						getSelectColorView().setColorEnabled(playerInfo.getColor(), false);
					}
				}
			}
		}

		if(!getSelectColorView().isModalShowing()){
			getSelectColorView().showModal();
		}

		if(game.getPlayers().size() > 0){
			for(int i = 0; i < game.getPlayers().size(); i++){
				game.getPlayers().get(i).setPlayerIndex(i);
			}
		}

		this.game = game;

		if(!getSelectColorView().isModalShowing()){
			getSelectColorView().showModal();
		}
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

		//check if game is null
		if(this.game == null){
			getMessageView().setTitle("Join Game Error");
			getMessageView().setMessage("Error joining game... game == null");
			getMessageView().showModal();
			return;
		}

		if(ServerProxy.getServer().gameJoin(this.game.getId(), color.toString().toLowerCase())){

			playerInfo.setColor(color);

			//this is the current playerInfo
			for(int i = 0; i < game.getPlayers().size(); i++){
				if(playerInfo.getName().equals(game.getPlayers().get(i).getName())){
					playerInfo.setPlayerIndex(i);
				}
			}
			if(playerInfo.getPlayerIndex() == -1){
				playerInfo.setPlayerIndex(game.getPlayers().size());
			}

			Game.getInstance().getPlayer().setPlayerInfo(playerInfo);
			Facade.getInstance().initializeData();

			//To set the color of the player in the GameInfo object as well
			for(int i = 0; i < this.game.getPlayers().size(); i++)
			{
				if(playerInfo.getName().equals(this.game.getPlayers().get(i).getName()))
				{
					this.game.getPlayers().get(i).setColor(playerInfo.getColor());
				}
			}

			Game.getInstance().setGameInfo(this.game);

			// If join succeeded
			getSelectColorView().closeModal();
			getJoinGameView().closeModal();


			ServerPoller.getPoller().startPoller();

			joinAction.execute();

		}
		else {
			getMessageView().setTitle("Error");
			getMessageView().setMessage("Error joining game... gameJoin command did not work!");
			getMessageView().showModal();
		}
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

		if(getJoinGameView().isModalShowing()){

//			start();
		}
	}

	public GameInfo getGame() {
		return game;
	}

	public void setGame(GameInfo game) {
		this.game = game;
	}

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public void setPlayerInfo(PlayerInfo playerInfo) {
		this.playerInfo = playerInfo;
	}
}

