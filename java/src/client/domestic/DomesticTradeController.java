package client.domestic;

import client.data.PlayerInfo;
import client.map.MapController;
import client.states.NotMyTurnState;
import client.states.PlayingState;
import command.player.AcceptTradeObject;
import command.player.OfferTradeObject;
import model.Facade;
import model.Game;
import model.Player;
import model.TurnTracker;
import model.cards_resources.ResourceCards;
import model.map.Map;
import server.ServerProxy;
import shared.definitions.*;
import client.base.*;
import client.misc.*;

import java.util.Arrays;
import java.util.List;
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
	private ResourceCards finalOffer;

	private int playerTradingWith; // Their playerIndex
	private int currentPlayerIndex;
	private Player currentPlayer;
	private PlayerInfo enemyPlayerInfo[];
	private OfferTradeObject currentOffer;

	private boolean[] sendingResources;
	private boolean[] receivingResources;
	private boolean accept;
	private boolean offered;
	private boolean waiting;
	private boolean firstInit;

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
		finalOffer = new ResourceCards(0,0,0,0,0);
		enemyPlayerInfo = new PlayerInfo[3];

		playerTradingWith = -1;

		// The user can choose if they want to send or receive EACH resource
		sendingResources = new boolean[5];
		receivingResources = new boolean[5];
		Arrays.fill(sendingResources, Boolean.FALSE);
		Arrays.fill(receivingResources, Boolean.FALSE);

		accept = false;
		offered = false;
		waiting = false;
		firstInit = true;

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
		if (playerTradingWith != -1 && resourcesToSend.size() > 0 && resourcesToReceive.size() > 0) {
			getTradeOverlay().setStateMessage("Send Trade Offer");
			return true;
		}
		else if (playerTradingWith == -1 && resourcesToSend.size() > 0 && resourcesToReceive.size() > 0) {
			getTradeOverlay().setStateMessage("Select a Player to Trade With");
			return false;
		}
		return false;
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		// Need to have at least 1 of the resource to decrease amount!
		if (resourcesToReceive.getResource(resource) > 0 && receivingResources[getResourceIndex(resource)]) {
			resourcesToReceive.subtractOneResource(resource);
			// Adjust what the Current Player can increase and decrease
			setResourceChangeEnabler(resource);
		}
		if (resourcesToSend.getResource(resource) > 0 && sendingResources[getResourceIndex(resource)]) {
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
		// Can request to receive as many resources as you want
		if (receivingResources[getResourceIndex(resource)]) {
			resourcesToReceive.addOneResource(resource);
			setResourceChangeEnabler(resource);
		}

		// Can only send resources equal to the amount the current player has
		if (sendingResources[getResourceIndex(resource)]) {
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
		getTradeOverlay().closeModal();
		getWaitOverlay().showModal();
		offered = true;
		waiting = true;

		setFinalOffer();

		ServerProxy.getServer().offerTrade(currentPlayerIndex, finalOffer, playerTradingWith);

		playerTradingWith = -1;
		resourcesToReceive = new ResourceCards(0,0,0,0,0);
		resourcesToSend = new ResourceCards(0,0,0,0,0);
	}

	public void setFinalOffer() {
		if (resourcesToReceive.getBrick() > 0) {
			finalOffer.setBrick(resourcesToReceive.getBrick());
		}
		if (resourcesToReceive.getOre() > 0) {
			finalOffer.setOre(resourcesToReceive.getOre());
		}
		if (resourcesToReceive.getSheep() > 0) {
			finalOffer.setSheep(resourcesToReceive.getSheep());
		}
		if (resourcesToReceive.getWheat() > 0) {
			finalOffer.setWheat(resourcesToReceive.getWheat());
		}
		if (resourcesToReceive.getWood() > 0) {
			finalOffer.setWood(resourcesToReceive.getWood());
		}
		if (resourcesToSend.getBrick() > 0) {
			finalOffer.setBrick(resourcesToSend.getBrick() * -1);
		}
		if (resourcesToSend.getOre() > 0) {
			finalOffer.setOre(resourcesToSend.getOre() * -1);
		}
		if (resourcesToSend.getSheep() > 0) {
			finalOffer.setSheep(resourcesToSend.getSheep() * -1);
		}
		if (resourcesToSend.getWheat() > 0) {
			finalOffer.setWheat(resourcesToSend.getWheat() * -1);
		}
		if (resourcesToSend.getWood() > 0) {
			finalOffer.setWood(resourcesToSend.getWood() * -1);
		}
	}

	public void setAcceptViewResources() {
		this.acceptOverlay.reset();
		accept = true;
		//Player enemyPlayer = Game.getInstance().getPlayersList().get(playerTradingWith);
		Player current = Game.getInstance().getPlayer();
		OfferTradeObject tradeOffer = Game.getInstance().getDomesticTradeInfo();
		ResourceCards cards = tradeOffer.getOffer();

		if (cards.getBrick() > 0) {
			getAcceptOverlay().addGiveResource(ResourceType.BRICK, cards.getBrick());
			if (cards.getBrick() > current.getResourceCards().getBrick()) {
				getAcceptOverlay().setAcceptEnabled(false);
			}
		}
		if (cards.getOre() > 0) {
			getAcceptOverlay().addGiveResource(ResourceType.ORE, cards.getOre());
			if (cards.getOre() > current.getResourceCards().getOre()) {
				getAcceptOverlay().setAcceptEnabled(false);
			}
		}
		if (cards.getSheep() > 0) {
			getAcceptOverlay().addGiveResource(ResourceType.SHEEP, cards.getSheep());
			if (resourcesToReceive.getSheep() > current.getResourceCards().getSheep()) {
				getAcceptOverlay().setAcceptEnabled(false);
			}
		}
		if (cards.getWheat() > 0) {
			getAcceptOverlay().addGiveResource(ResourceType.WHEAT, cards.getWheat());
			if (resourcesToReceive.getWheat() > current.getResourceCards().getWheat()) {
				getAcceptOverlay().setAcceptEnabled(false);
			}
		}
		if (cards.getWood() > 0) {
			getAcceptOverlay().addGiveResource(ResourceType.WOOD, cards.getWood());
			if (resourcesToReceive.getWood() > current.getResourceCards().getWood()) {
				getAcceptOverlay().setAcceptEnabled(false);
			}
		}

		if (cards.getBrick() < 0) {
			getAcceptOverlay().addGetResource(ResourceType.BRICK, cards.getBrick());
		}
		if (cards.getOre() < 0) {
			getAcceptOverlay().addGetResource(ResourceType.ORE, cards.getOre());
		}
		if (cards.getSheep() < 0) {
			getAcceptOverlay().addGetResource(ResourceType.SHEEP, cards.getSheep());
		}
		if (cards.getWheat() < 0) {
			getAcceptOverlay().addGetResource(ResourceType.WHEAT, cards.getWheat());
		}
		if (cards.getWood() < 0) {
			getAcceptOverlay().addGetResource(ResourceType.WOOD, cards.getWood());
		}
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		if (resourcesToSend.size() > 0 && resourcesToReceive.size() > 0) {
			playerTradingWith = playerIndex;
		}
		else {
			getTradeOverlay().setStateMessage("Must Finalize Trade Details Before Selecting A Player");
		}
		getTradeOverlay().setTradeEnabled(canTrade());
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
		currentPlayerIndex = Game.getInstance().getPlayer().getPlayerInfo().getPlayerIndex();
		ServerProxy.getServer().acceptTrade(currentPlayerIndex, willAccept);
		getAcceptOverlay().closeModal();
		accept = false;
		Game.getInstance().setDomesticTradeInfo(null);
	}

	/**
	 * This method is called whenever the observed object is changed. An application calls an
	 * Observable object's notifyObservers method to have all the object's observers notified of the change.
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (MapController.getState().canMakeTrades()) {
			getTradeView().enableDomesticTrade(true);
			if (firstInit) {
				List<PlayerInfo> playerInfoData = Game.getInstance().getGameInfo().getPlayers();
				int count = 0;
				for (int i = 0; i < playerInfoData.size(); i++) {
					if (!playerInfoData.get(i).getName().equals(Game.getInstance().getCurrentPlayerInfo().getName())) {
						enemyPlayerInfo[count] = playerInfoData.get(i);
						count++;
					}
				}
				getTradeOverlay().setPlayers(enemyPlayerInfo);
				firstInit = false;
			}
		}
		else {
			getTradeView().enableDomesticTrade(false);
		}
		// Need to examine 2 different perspectives here
		//AcceptTradeObject currentAccept = Game.getInstance().getAcceptTrade();

		// Depending on if you are the receiver or sender you have different views
		if (MapController.getState() instanceof PlayingState && Game.getInstance().getDomesticTradeInfo() != null) {
			currentOffer = Game.getInstance().getDomesticTradeInfo();
			currentPlayerIndex = Game.getInstance().getPlayer().getPlayerInfo().getPlayerIndex();
			if (currentPlayerIndex == currentOffer.getPlayerIndex()) {
				if (!offered) {
					offered = true;
					waiting = true;
					getWaitOverlay().showModal();
				}
			}
		}
		else {
			if (Game.getInstance().getPlayer().getPlayerInfo() != null && Game.getInstance().getDomesticTradeInfo() != null) {
				currentOffer = Game.getInstance().getDomesticTradeInfo();
				currentPlayerIndex = Game.getInstance().getPlayer().getPlayerInfo().getPlayerIndex();
				if (currentPlayerIndex == currentOffer.getReceiver()) {
					if(!accept) {
						setAcceptViewResources();
						this.acceptOverlay.showModal();
					}
				}
			}
		}
		if (MapController.getState() instanceof PlayingState && Game.getInstance().getDomesticTradeInfo() == null) {
			getWaitOverlay().closeModal();
		}
	}
}

