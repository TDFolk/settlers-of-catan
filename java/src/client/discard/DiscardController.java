package client.discard;

import decoder.JsonModels.JsonTurnTracker;
import model.Facade;
import model.Game;
import model.cards_resources.ResourceCards;
import shared.definitions.*;
import client.base.*;
import client.misc.*;

import java.util.Observable;
import java.util.Observer;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController, Observer {

	private IWaitView waitView;
	// This really should be for the model
	// Facade.getInstance().getPhase() == "discardCards"
	private JsonTurnTracker turnTracker = new JsonTurnTracker();
	private int cardsToDiscard;
	private ResourceCards playersCards;
	private int numCardsPickedToDiscard;
	private ResourceCards resourcesPickedToDiscard;
	
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);
		
		this.waitView = waitView;

		// This Controller will now be notified to any changes in the Game Object
		Game.getInstance().addObserver(this);
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		switch(resource) {
			case BRICK:
				resourcesPickedToDiscard.setBrick(resourcesPickedToDiscard.getBrick() + 1);
				break;
			case ORE:
				resourcesPickedToDiscard.setOre(resourcesPickedToDiscard.getOre() + 1);
				break;
			case SHEEP:
				resourcesPickedToDiscard.setSheep(resourcesPickedToDiscard.getSheep() + 1);
				break;
			case WHEAT:
				resourcesPickedToDiscard.setWheat(resourcesPickedToDiscard.getWheat() + 1);
				break;
			case WOOD:
				resourcesPickedToDiscard.setWood(resourcesPickedToDiscard.getWood() + 1);
				break;
		}
		numCardsPickedToDiscard++;
		this.updateDiscardView();
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		switch(resource) {
			case BRICK:
				resourcesPickedToDiscard.setBrick(resourcesPickedToDiscard.getBrick() - 1);
				break;
			case ORE:
				resourcesPickedToDiscard.setOre(resourcesPickedToDiscard.getOre() - 1);
				break;
			case SHEEP:
				resourcesPickedToDiscard.setSheep(resourcesPickedToDiscard.getSheep() - 1);
				break;
			case WHEAT:
				resourcesPickedToDiscard.setWheat(resourcesPickedToDiscard.getWheat() - 1);
				break;
			case WOOD:
				resourcesPickedToDiscard.setWood(resourcesPickedToDiscard.getWood() - 1);
				break;
		}
		numCardsPickedToDiscard--;
		this.updateDiscardView();
	}

	@Override
	public void discard() {
		if (resourcesPickedToDiscard.size() == cardsToDiscard) {
			getDiscardView().closeModal();
			Game.getInstance().getPlayer().getResourceCards().reduceResources(resourcesPickedToDiscard);
		}
	}

	/**
	 * This method is called whenever the observed object is changed. An application calls an
	 * Observable object's notifyObservers method to have all the object's observers notified of the change.
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		// The Game class has a list of Players and also a single Player?
		// How do controllers know who's turn it is?
		int currentPlayerIndex = Game.getInstance().getPlayer().getPlayerInfo().getPlayerIndex();

		if (arg.equals("reset")) {
			return;
		}
		// DiscardCardsObject (Json)
		if (turnTracker.getStatus() == "discardCards") {
			// Player has over 7 cards
			if (Game.getInstance().getPlayer().overResourceLimit()) {
				// The DiscardView UI is not showing
				if (!getDiscardView().isModalShowing()) {
					// Have to discard half their resources
					cardsToDiscard = (Game.getInstance().getPlayer().getResourceCards().size() / 2);
					playersCards = Game.getInstance().getPlayer().getResourceCards();
					numCardsPickedToDiscard = 0;
					resourcesPickedToDiscard = new ResourceCards(0,0,0,0,0);
					// Show the UI
					getDiscardView().showModal();
					// Update the UI DiscardView
					this.updateDiscardView();
				}
			}
			else {
				// Close down any modals that may be showing?
			}
		}
	}

	public void updateDiscardView() {
		// A player chooses which resources AND how many of each resources they wish to discard.
		// This manages if they can increase or decrease that amount when deciding.
		// If they had half a deck of sheep, they could discard all sheep and no other resource if they choose
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK,
				(numCardsPickedToDiscard < cardsToDiscard && resourcesPickedToDiscard.getBrick() < playersCards.getBrick()),
				resourcesPickedToDiscard.getBrick() > 0);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE,
				(numCardsPickedToDiscard < cardsToDiscard && resourcesPickedToDiscard.getOre() < playersCards.getOre()),
				resourcesPickedToDiscard.getOre() > 0);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP,
				(numCardsPickedToDiscard < cardsToDiscard && resourcesPickedToDiscard.getSheep() < playersCards.getSheep()),
				resourcesPickedToDiscard.getSheep() > 0);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT,
				(numCardsPickedToDiscard < cardsToDiscard && resourcesPickedToDiscard.getWheat() < playersCards.getWheat()),
				resourcesPickedToDiscard.getWheat() > 0);
		getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD,
				(numCardsPickedToDiscard < cardsToDiscard && resourcesPickedToDiscard.getWood() < playersCards.getWood()),
				resourcesPickedToDiscard.getWood() > 0);

		getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, resourcesPickedToDiscard.getBrick());
		getDiscardView().setResourceDiscardAmount(ResourceType.ORE, resourcesPickedToDiscard.getOre());
		getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, resourcesPickedToDiscard.getSheep());
		getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, resourcesPickedToDiscard.getWheat());
		getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, resourcesPickedToDiscard.getWood());

		getDiscardView().setResourceMaxAmount(ResourceType.BRICK, playersCards.getBrick());
		getDiscardView().setResourceMaxAmount(ResourceType.ORE, playersCards.getOre());
		getDiscardView().setResourceMaxAmount(ResourceType.SHEEP, playersCards.getSheep());
		getDiscardView().setResourceMaxAmount(ResourceType.WHEAT, playersCards.getWheat());
		getDiscardView().setResourceMaxAmount(ResourceType.WOOD, playersCards.getWood());

		getDiscardView().setDiscardButtonEnabled(numCardsPickedToDiscard == cardsToDiscard);

		getDiscardView().setStateMessage(numCardsPickedToDiscard + "/" + cardsToDiscard);

	}
}

