package client.devcards;

import model.Game;
import model.Player;
import model.cards_resources.DevelopmentCard;
import model.cards_resources.ResourceCards;
import model.cards_resources.Trade;
import server.ServerProxy;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import client.base.*;

import java.util.Observable;
import java.util.Observer;

import exception.CardException;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController, Observer {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;

		// This Controller will now be notified to any changes in the Game Object
		Game.getInstance().addObserver(this);
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		
		getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() {
		
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		Player player = Game.getInstance().getPlayer();
		int index = player.getPlayerInfo().getPlayerIndex();
		ServerProxy.getServer().buyDevCard(index);
		
		/*
		try {
			player.buyDevelopmentCard();
		} catch (Exception e) {
			System.out.println(e);
		}
		*/
		
		getBuyCardView().closeModal();
	}

	@Override
	public void startPlayCard() {
		
		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {

		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		Player player = Game.getInstance().getPlayer();
		int index = player.getPlayerInfo().getPlayerIndex();
		
		ServerProxy.getServer().monopoly(resource.name(), index);
		
		/*
		try {
			player.playDevelopmentCard(DevCardType.MONOPOLY);
		} catch (CardException e) { System.out.println(e); return; }
		player.setPlayedDevCard(true);
		
		ResourceCards steal;
		switch (resource) {
		case BRICK: steal = new ResourceCards(1, 0, 0, 0, 0); break;
		case ORE: steal = new ResourceCards(0, 1, 0, 0, 0); break;
		case SHEEP: steal = new ResourceCards(0, 0, 1, 0, 0); break;
		case WHEAT: steal = new ResourceCards(0, 0, 0, 1, 0); break;
		case WOOD: steal = new ResourceCards(0, 0, 0, 0, 1); break;
		default: steal = new ResourceCards(1, 0, 0, 0, 0);
		}
		
		for (Player p : Game.getInstance().getPlayersList()) {
			
			if (p.getPlayerInfo().getPlayerIndex() != player.getPlayerInfo().getPlayerIndex()) {
				Trade trade = new Trade(player.getPlayerInfo().getPlayerIndex(),
						p.getPlayerInfo().getPlayerIndex(),
						new ResourceCards(0,0,0,0,0), steal);

				while (trade.canTrade()) {
					trade.executeTrade();
				}
			}
		}
		*/
	}

	@Override
	public void playMonumentCard() {
		Player player = Game.getInstance().getPlayer();
		int index = player.getPlayerInfo().getPlayerIndex();
		
		ServerProxy.getServer().monument(index);
		
		/*
		try {
			player.playDevelopmentCard(DevCardType.MONUMENT);
		} catch (CardException e) { System.out.println(e); return; }
		player.setPlayedDevCard(true);
		
		player.addMonument();
		*/
	}

	@Override
	public void playRoadBuildCard() {
		Player player = Game.getInstance().getPlayer();
		int index = player.getPlayerInfo().getPlayerIndex();
		
		ServerProxy.getServer().roadBuilding(index, null, null);
		
		/*
		try {
			player.playDevelopmentCard(DevCardType.ROAD_BUILD);
		} catch (CardException e) { System.out.println(e); return; }
		player.setPlayedDevCard(true);
		
		// do ROAD BUiLD
		 */
		 
		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {
		Player player = Game.getInstance().getPlayer();
		int index = player.getPlayerInfo().getPlayerIndex();
		
		ServerProxy.getServer().soldier(index, -1, null);
		
		/*
		try {
			player.playDevelopmentCard(DevCardType.SOLDIER);
		} catch (CardException e) { System.out.println(e); return; }
		player.setPlayedDevCard(true);
		
		// do SoLDiER
		 */
		
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		Player player = Game.getInstance().getPlayer();
		int index = player.getPlayerInfo().getPlayerIndex();
		
		ServerProxy.getServer().yearOfPlenty(index, resource1, resource2);
		
		/*
		try {
			player.playDevelopmentCard(DevCardType.YEAR_OF_PLENTY);
		} catch (CardException e) { System.out.println(e); return; }
		player.setPlayedDevCard(true);
		
		try {
			if (Game.getInstance().getBank().canDrawResourceCard(resource1))
				player.drawResourceCard(resource1);
		} catch (CardException e) {}
		try {
			if (Game.getInstance().getBank().canDrawResourceCard(resource2))
				player.drawResourceCard(resource2);
		} catch (CardException e) {}
		*/
	}

	/**
	 * This method is called whenever the observed object is changed. An application calls an
	 * Observable object's notifyObservers method to have all the object's observers notified of the change.
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		Player player = Game.getInstance().getPlayer();
		
		int mono = 0;
		int monu = 0;
		int road = 0;
		int sold = 0;
		int year = 0;
		
		for (DevelopmentCard card : player.getDevelopmentCards()) {
			switch (card.getType()) {
			case MONOPOLY: mono++; break;
			case MONUMENT: monu++; break;
			case ROAD_BUILD: road++; break;
			case SOLDIER: sold++; break;
			case YEAR_OF_PLENTY: year++; break;
			}
		}
		
		getPlayCardView().setCardAmount(DevCardType.MONOPOLY, mono);
		getPlayCardView().setCardAmount(DevCardType.MONUMENT, monu);
		getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, road);
		getPlayCardView().setCardAmount(DevCardType.SOLDIER, sold);
		getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, year);
	}
}

