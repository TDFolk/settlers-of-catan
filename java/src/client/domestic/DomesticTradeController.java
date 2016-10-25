package client.domestic;

import model.Game;
import model.cards_resources.ResourceCards;
import server.ServerProxy;
import shared.definitions.*;
import client.base.*;
import client.misc.*;

import java.util.Observable;
import java.util.Observer;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController, Observer {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;

	private ResourceCards cardsBeingOffered;
	private ResourceCards originalPlayersCards;
	private ResourceCards playersCardsTemp;
	private int playerToTradeTo;
	private boolean receive;

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
		receive = false;
		//TODO originalPlayersCards = Game.getInstance().getPlayer().getResourceCards();
		//TODO playersCardsTemp = Game.getInstance().getPlayer().getResourceCards();

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

		getTradeOverlay().showModal();
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		int playerResourceAmount = playersCardsTemp.getResource(resource);

		if (playerResourceAmount > 0) {
			cardsBeingOffered.subtractOneResource(resource);
			playersCardsTemp.addOneResource(resource, originalPlayersCards.getResource(resource));
		}

		int currentAmount = playersCardsTemp.getResource(resource);

		if (currentAmount == 0) {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
		}
		else if (currentAmount == originalPlayersCards.getResource(resource)) {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, false, true);
		}
		else {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
		}
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
		int playerResourceAmount = playersCardsTemp.getResource(resource);

		if (playerResourceAmount > 0) {
			cardsBeingOffered.addOneResource(resource, originalPlayersCards.getResource(resource));
			playersCardsTemp.subtractOneResource(resource);
		}

		int currentAmount = playersCardsTemp.getResource(resource);

		if (currentAmount == 0) {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, false, true);
		}
		else if (currentAmount == originalPlayersCards.getResource(resource)) {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
		}
		else {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
		}
	}

	@Override
	public void sendTradeOffer() {
		if (getTradeOverlay().isModalShowing()) {
			getTradeOverlay().closeModal();
		}

		if (!getWaitOverlay().isModalShowing()) {
			getWaitOverlay().showModal();
		}

		int playerIndex = Game.getInstance().getPlayer().getPlayerInfo().getPlayerIndex();
		ServerProxy.getServer().offerTrade(playerIndex, cardsBeingOffered, playerToTradeTo);
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		playerToTradeTo = playerIndex;
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		receive = true;
		getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		receive = false; //aka we are going to send
		if (Game.getInstance().getPlayer().getResourceCards().getResource(resource) == 0) {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, false, false);
		} else {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
		}

	}

	@Override
	public void unsetResource(ResourceType resource) {
		cardsBeingOffered.resetOneResource(resource);
		getTradeOverlay().setResourceAmount(resource, "0");


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

