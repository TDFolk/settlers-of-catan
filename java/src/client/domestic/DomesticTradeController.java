package client.domestic;

import model.Game;
import model.Player;
import model.cards_resources.ResourceCards;
import server.ServerProxy;
import shared.definitions.*;
import client.base.*;
import client.misc.*;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController, Observer {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;

	private ResourceCards resourcesToSend;
	private ResourceCards resourcesToReceive;

	private int playerTradingWith; // Their playerIndex
	private int currentPlayerIndex;
	private Player currentPlayer;

	private boolean[] sendingResources;
	private boolean[] receivingResources;
	private boolean accept;
	private boolean offered;
	private boolean waiting;

	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
									IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);
		
		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);

		this.getTradeView().enableDomesticTrade(false);
		resourcesToSend = new ResourceCards(0,0,0,0,0);
		resourcesToReceive = new ResourceCards(0,0,0,0,0);

		playerTradingWith = -1;

		// The user can choose if they want to send or receive EACH resource
		sendingResources = new boolean[5];
		receivingResources = new boolean[5];
		Arrays.fill(sendingResources, Boolean.FALSE);
		Arrays.fill(receivingResources, Boolean.FALSE);

		accept = false;
		offered = false;
		waiting = false;


		// This Controller will now be notified to any changes in the Game Object
		Game.getInstance().addObserver(this);
	}
	
	public IDomesticTradeView getTradeView() {
		
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}

	@Override
	public void startTrade() {
		// reset booleans offered and waiting
		offered = false;
		waiting = false;

		// Whether or not the user can select resources to trade.
		getTradeOverlay().setResourceSelectionEnabled(false);

		// Determine the current client's player info
		currentPlayer = Game.getInstance().getPlayer();
		currentPlayerIndex = Game.getInstance().getPlayer().getPlayerInfo().getPlayerIndex();

		getTradeOverlay().setResourceSelectionEnabled(true);
		// Enables or disables the ability to select a player to trade with.
		// Whether or not player selection is currently allowed
		getTradeOverlay().setPlayerSelectionEnabled(true);
		getTradeOverlay().showModal();
	}

	public boolean canTrade() {
		if (playerTradingWith != -1 && resourcesToSend.size() != 0 && resourcesToReceive.size() != 0) {
			getTradeOverlay().setStateMessage("Trade at your own risk");
			return true;
		}
		else {
			getTradeOverlay().setStateMessage("Let's see if we can make a deal");
			return false;
		}
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		// Need to have at least 1 of the resource to decrease amount!
		if (resourcesToReceive.getResource(resource) > 0) {
			resourcesToReceive.subtractOneResource(resource);
			// Adjust what the Current Player can increase and decrease
			setResourceChangeEnabler(resource);
		}
		if (resourcesToSend.getResource(resource) > 0) {
			resourcesToSend.subtractOneResource(resource);
			setResourceChangeEnabler(resource);
		}
		// Now we need to determine if a trade is valid or not after these changes
		// to determine if we should enable the setTradeEnable
		getTradeOverlay().setTradeEnabled(canTrade());
	}


	/**
	 * Called by the domestic trade overlay when the user increases the amount
	 * of a resource.
	 *
	 * @param resource
	 *            The resource whose amount is being increased
	 */
	@Override
	public void increaseResourceAmount(ResourceType resource) {
		Player enemyPlayer = Game.getInstance().getPlayersList().get(playerTradingWith);
		int enemyPlayerResourceAmount = enemyPlayer.getResourceCards().getResource(resource);

		// Can only receive resources equal to the amount the player your trading with has
		if (resourcesToReceive.getResource(resource) != enemyPlayerResourceAmount) {
			resourcesToReceive.addOneResource(resource);
			setResourceChangeEnabler(resource);
		}

		int currentPlayerResourceAmount = currentPlayer.getResourceCards().getResource(resource);

		// Can only send resources equal to the amount the current player has
		if (resourcesToSend.getResource(resource) != currentPlayerResourceAmount) {
			resourcesToSend.addOneResource(resource);
			setResourceChangeEnabler(resource);
		}

		getTradeOverlay().setTradeEnabled(canTrade());
	}

	public void setResourceChangeEnabler(ResourceType resourceType) {
		// Determine is this given resource is attempting to be sent or received
		if (receivingResources[getResourceIndex(resourceType)]) {
			if (resourcesToReceive.getResource(resourceType) == 0) {
				// Starts at 0 and the user can increase to request more of that resource, but can't decrease past 0!
				getTradeOverlay().setResourceAmountChangeEnabled(resourceType, true, false);
			}
			else {
				getTradeOverlay().setResourceAmountChangeEnabled(resourceType, true, true);
			}
		}
		else if (sendingResources[getResourceIndex(resourceType)]) {
			if (resourcesToSend.getResource(resourceType) == 0) {
				if (resourcesToSend.getResource(resourceType) == currentPlayer.getResourceCards().getResource(resourceType)) {
					// Current Player has 0 of this resource type! Can't offer to send a resource away you don't have.
					getTradeOverlay().setResourceAmountChangeEnabled(resourceType, false, false);
				}
				else {
					// There is at least 1 resource to send if desired, but can't decrease past 0
					getTradeOverlay().setResourceAmountChangeEnabled(resourceType, true, false);
				}
			}
			else if(resourcesToSend.getResource(resourceType) == currentPlayer.getResourceCards().getResource(resourceType)) {
				// Current Player has already offered to send max amount for this resource, can only decrease from here!
				getTradeOverlay().setResourceAmountChangeEnabled(resourceType, false, true);
			}
			else {
				// Not at 0, but not at the max, increase or decrease.. whatever your heat desires
				getTradeOverlay().setResourceAmountChangeEnabled(resourceType, true, true);
			}
		}
		// If not sending or receiving, then the none option is selected
	}

	public int getResourceIndex(ResourceType resourceType) {
		switch (resourceType) {
			case BRICK:
				return 0;
			case ORE:
				return 1;
			case SHEEP:
				return 2;
			case WHEAT:
				return 3;
			case WOOD:
				return 4;
		}
		return -1; // failed
	}

	@Override
	public void sendTradeOffer() {
		if (getTradeOverlay().isModalShowing()) {
			getTradeOverlay().closeModal();
		}

		if (!getWaitOverlay().isModalShowing()) {
			getWaitOverlay().showModal();
		}

		ServerProxy.getServer().offerTrade(currentPlayerIndex, resourcesToSend, playerTradingWith);
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		playerTradingWith = playerIndex;
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		receivingResources[getResourceIndex(resource)] = true;
		sendingResources[getResourceIndex(resource)] = false;

		// The current player changed his scope, reset resource back to 0
		resourcesToReceive.resetOneResource(resource);
		resourcesToSend.resetOneResource(resource);

		getTradeOverlay().setResourceAmount(resource, "0");
		setResourceChangeEnabler(resource);
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		sendingResources[getResourceIndex(resource)] = true;
		receivingResources[getResourceIndex(resource)] = false;

		resourcesToSend.resetOneResource(resource);
		resourcesToReceive.resetOneResource(resource);

		getTradeOverlay().setResourceAmount(resource, "0");
		setResourceChangeEnabler(resource);

	}

	@Override
	public void unsetResource(ResourceType resource) {
		// When the none option is selected
		receivingResources[getResourceIndex(resource)] = false;
		sendingResources[getResourceIndex(resource)] = false;

		resourcesToReceive.resetOneResource(resource);
		resourcesToSend.resetOneResource(resource);

		getTradeOverlay().setResourceAmount(resource, "0");
		setResourceChangeEnabler(resource);

		// Possible to trade now?
		getTradeOverlay().setTradeEnabled(canTrade());
	}

	@Override
	public void cancelTrade() {
		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		int currentPlayerIndex = Game.getInstance().getPlayer().getPlayerInfo().getPlayerIndex();
		ServerProxy.getServer().acceptTrade(currentPlayerIndex, willAccept);
		getAcceptOverlay().closeModal();
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

