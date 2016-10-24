package client.resources;

import java.util.*;

import client.base.*;
import client.states.IGameState;
import client.states.PlayingState;
import model.Facade;
import model.Game;
import shared.definitions.ResourceType;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController, Observer {

	private Map<ResourceBarElement, IAction> elementActions;
	
	public ResourceBarController(IResourceBarView view) {

		super(view);
		
		elementActions = new HashMap<ResourceBarElement, IAction>();

		// This Controller will now be notified to any changes in the Game Object
		Game.getInstance().addObserver(this);
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {

		elementActions.put(element, action);
	}

	@Override
	public void buildRoad() {
		executeElementAction(ResourceBarElement.ROAD);
	}

	@Override
	public void buildSettlement() {
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}

	@Override
	public void buildCity() {
		executeElementAction(ResourceBarElement.CITY);
	}

	@Override
	public void buyCard() {
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	@Override
	public void playCard() {
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}
	
	private void executeElementAction(ResourceBarElement element) {
		
		if (elementActions.containsKey(element)) {
			
			IAction action = elementActions.get(element);
			action.execute();
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
		getView().setElementAmount(ResourceBarElement.BRICK, Facade.getInstance().getPlayerResource(ResourceType.BRICK));
		getView().setElementAmount(ResourceBarElement.ORE, Facade.getInstance().getPlayerResource(ResourceType.ORE));
		getView().setElementAmount(ResourceBarElement.SHEEP, Facade.getInstance().getPlayerResource(ResourceType.SHEEP));
		getView().setElementAmount(ResourceBarElement.WHEAT, Facade.getInstance().getPlayerResource(ResourceType.WHEAT));
		getView().setElementAmount(ResourceBarElement.WOOD, Facade.getInstance().getPlayerResource(ResourceType.WOOD));

		getView().setElementAmount(ResourceBarElement.CITY, Facade.getInstance().getPlayerCities());
		getView().setElementAmount(ResourceBarElement.SETTLEMENT, Facade.getInstance().getPlayerSettlements());
		getView().setElementAmount(ResourceBarElement.ROAD, Facade.getInstance().getPlayerRoads());

		getView().setElementAmount(ResourceBarElement.SOLDIERS, Facade.getInstance().getPlayerRoads());

		getView().setElementEnabled(ResourceBarElement.BUY_CARD, (arg instanceof PlayingState) && Facade.getInstance().canBuyDevelopmentCard());
		getView().setElementEnabled(ResourceBarElement.CITY, (arg instanceof PlayingState) && Facade.getInstance().canBuyCity());
		getView().setElementEnabled(ResourceBarElement.SETTLEMENT, (arg instanceof PlayingState) && Facade.getInstance().canBuySettlement());
		getView().setElementEnabled(ResourceBarElement.ROAD, (arg instanceof PlayingState) && Facade.getInstance().canBuyRoad());
	}
}

