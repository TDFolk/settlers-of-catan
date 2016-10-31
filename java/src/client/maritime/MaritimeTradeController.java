package client.maritime;

import client.map.MapController;
import client.states.PlayingState;
import model.Facade;
import model.Game;
import server.ServerProxy;
import shared.definitions.*;
import client.base.*;

import java.util.Observable;
import java.util.Observer;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer {

	private IMaritimeTradeOverlay tradeOverlay;
	
	private ResourceType getResource;
	private ResourceType giveResource;
	private boolean threeForOne;
	private ResourceType[] twoForOnes;
	private ResourceType[] gettableResources;
	private ResourceType[] givableResources;
	private String stateMessageGive = "Select the resource you'd like to give.";
	private String stateMessageGet = "Select the resource you'd like to get.";

	private static ResourceType[] ALL_RESOURCES = new ResourceType[]{ResourceType.BRICK, ResourceType.ORE, ResourceType.SHEEP, ResourceType.WHEAT, ResourceType.WOOD};

	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {

		super(tradeView);

		setTradeOverlay(tradeOverlay);

		// This Controller will now be notified to any changes in the Game Object
		Game.getInstance().addObserver(this);
	}

	public IMaritimeTradeView getTradeView() {

		return (IMaritimeTradeView) super.getView();
	}

	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	@Override
	public void startTrade() {
		threeForOne = Facade.getInstance().canPortTrade(null);
		twoForOnes = new ResourceType[5];
		int i = 0;
		for (ResourceType resource : ALL_RESOURCES) {
			if (Facade.getInstance().canPortTrade(resource)) {
				twoForOnes[i++] = resource;
			}
		}
		setGettableResources();
		setGivableResources();
		tradeOverlay.showGiveOptions(givableResources);
		tradeOverlay.hideGetOptions();
		tradeOverlay.setCancelEnabled(true);
		tradeOverlay.setTradeEnabled(false);
		tradeOverlay.setStateMessage(stateMessageGive);
		getTradeOverlay().showModal();
	}

	@Override
	public void makeTrade() {
		int ratio;
		if (hasResourcePort(giveResource)) {
			ratio = 2;
		} else if (threeForOne) {
			ratio = 3;
		} else {
			ratio = 4;
		}

		String model = ServerProxy.getServer().maritimeTrade(Facade.getInstance().getPlayerIndex(), ratio, giveResource.name(), getResource.name());
		Facade.getInstance().replaceModel(model);

		tradeOverlay.reset();
		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {
		tradeOverlay.reset();
		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) {
		getResource = resource;
		tradeOverlay.selectGetOption(resource, 1);
		tradeOverlay.setTradeEnabled(true);
		tradeOverlay.setStateMessage("You want to trade away your" + giveResource.name() + " and in return receive " + getResource.name() + "?");
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		giveResource = resource;
		tradeOverlay.selectGiveOption(resource, hasResourcePort(resource) ? 2 : (threeForOne ? 3 : 4));
		tradeOverlay.showGetOptions(gettableResources);
		tradeOverlay.hideGiveOptions();
		tradeOverlay.setStateMessage(stateMessageGet);
	}

	@Override
	public void unsetGetValue() {
		tradeOverlay.showGetOptions(gettableResources);
		tradeOverlay.setTradeEnabled(false);
		tradeOverlay.setStateMessage(stateMessageGet);
	}

	@Override
	public void unsetGiveValue() {
		tradeOverlay.showGiveOptions(givableResources);
		tradeOverlay.hideGetOptions();
		tradeOverlay.setStateMessage(stateMessageGive);
	}

	/**
	 * This method is called whenever the observed object is changed. An application calls an
	 * Observable object's notifyObservers method to have all the object's observers notified of the change.
	 *
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {

		if (MapController.getState() instanceof PlayingState) {

			getTradeView().enableMaritimeTrade(Facade.getInstance().canPortTrade());
		}
	}

	private boolean hasResourcePort(ResourceType resource) {
		for (ResourceType twoForOne : twoForOnes) {
			if (resource == twoForOne) {
				return true;
			}
		}
		return false;
	}

	private void setGettableResources() {
		gettableResources = new ResourceType[5];
		int i = 0;
		for (ResourceType resource : ALL_RESOURCES) {
			if (Facade.getInstance().canDrawResourceCard(resource)) {
				gettableResources[i++] = resource;
			}
		}
	}

	private void setGivableResources() {
		givableResources = new ResourceType[5];
		if (threeForOne) {
			int i = 0;
			for (ResourceType resource : ALL_RESOURCES){
				if (Facade.getInstance().getPlayerResource(resource) >= 3 ||
						Facade.getInstance().getPlayerResource(resource) >= 2 && Facade.getInstance().canPortTrade(resource)) {
					givableResources[i++] = resource;
				}
			}
		} else {
			int i = 0;
			for (ResourceType resource : ALL_RESOURCES) {
				if (Facade.getInstance().getPlayerResource(resource) >= 4 ||
						Facade.getInstance().getPlayerResource(resource) >= 2 && Facade.getInstance().canPortTrade(resource)) {
					givableResources[i++] = resource;
				}
			}
		}
	}
}

