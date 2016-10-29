package client.map;

import java.util.*;

import client.states.FirstRoundState;
import client.states.IGameState;
import client.states.NotMyTurnState;
import client.states.SecondRoundState;
import model.Facade;
import model.Game;
import model.TurnTracker;
import model.map.*;
import model.pieces.Building;
import model.pieces.City;
import model.pieces.Road;
import model.pieces.Settlement;
import server.ServerProxy;
import shared.definitions.*;
import shared.locations.*;
import client.base.*;
import client.data.*;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController, Observer {

	private static boolean gameStarted = false;
	private IRobView robView;
	/**
	 * to initialize a new state
	 *      eg: state = new PlayingState();
	 * call a function on the state
	 *      eg: state.canBuildRoad(edgeLocation);
	 * depending on the state, each function will return something different
	 */
	private static IGameState state;
	private EdgeLocation firstRoad;
	HexLocation robberLocation;
	private boolean init = false;
	//????
	RobPlayerInfo[] empty = {};

	private boolean firstRoundDone = false;
	private boolean secondRoundDone = false;

	private List<Road> roadList = new ArrayList<>();
	private List<Settlement> settlementList = new ArrayList<>();
	
	public MapController(IMapView view, IRobView robView) {
		
		super(view);
		
		setRobView(robView);
		initFromModel();

		// This Controller will now be notified to any changes in the Game Object
		Game.getInstance().addObserver(this);
		state = new NotMyTurnState();
	}
	
	public IMapView getView() {
		
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView() {
		return robView;
	}
	private void setRobView(IRobView robView) {
		this.robView = robView;
	}
	
	protected void initFromModel() {

		if(Game.getInstance().getGameInfo() != null){
			//checking if map is ever null... it should always be true if it gets here though
			if(Game.getInstance().getMap() != null){
				init = true;
				//put water on the board
				water();
				//List hexes -> array hexes
				Hex[] hexes = new Hex[Game.getInstance().getMap().getHexes().size()];
				hexes = Game.getInstance().getMap().getHexes().toArray(hexes);
				for(Hex hex : hexes){
					if(hex.getHexType() == HexType.DESERT){
						//this is the robber information
						getView().addHex(hex.getLocation(), hex.getHexType());
						getView().placeRobber(hex.getLocation());
						robberLocation = hex.getLocation();
					}
					else {
						getView().addHex(hex.getLocation(), hex.getHexType());
						getView().addNumber(hex.getLocation(), hex.getNumberToken());

					}
				}

				List<Port> ports = Game.getInstance().getMap().getPorts();
				for(Port port : ports){
					if(port.getResourceType() == null){
						getView().addPort(new EdgeLocation(port.getLocation(), port.getDirection()), PortType.THREE);
					}
					else {
						getView().addPort(new EdgeLocation(port.getLocation(), port.getDirection()), PortType.valueOf(port.getResourceType().toString()));
					}
				}
			}
		}
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		
		return true;
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		
		return true;
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		
		return true;
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		
		return true;
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		getView().placeRoad(edgeLoc, Game.getInstance().getCurrentPlayerInfo().getColor());
		if(state instanceof FirstRoundState || state instanceof SecondRoundState){
			ServerProxy.getServer().buildRoad(Game.getInstance().getCurrentPlayerInfo().getPlayerIndex(), edgeLoc, true);
			roadList.add(new Road(Game.getInstance().getCurrentPlayerInfo().getColor(), edgeLoc));


			//is this the right place to finish the turn????
			ServerProxy.getServer().finishTurn(Game.getInstance().getCurrentPlayerInfo().getPlayerIndex());
		}
	}

	public void placeSettlement(VertexLocation vertLoc) {
		getView().placeSettlement(vertLoc, Game.getInstance().getCurrentPlayerInfo().getColor());
		if(state instanceof FirstRoundState || state instanceof SecondRoundState){
			ServerProxy.getServer().buildSettlement(Game.getInstance().getCurrentPlayerInfo().getPlayerIndex(), vertLoc, true);
			settlementList.add(new Settlement(Game.getInstance().getCurrentPlayerInfo().getColor(), vertLoc));
		}
	}

	public void placeCity(VertexLocation vertLoc) {
		
		getView().placeCity(vertLoc, Game.getInstance().getCurrentPlayerInfo().getColor());
	}

	public void placeRobber(HexLocation hexLoc) {
		
		getView().placeRobber(hexLoc);
		
		getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, Game.getInstance().getCurrentPlayerInfo().getColor(), true);
	}
	
	public void cancelMove() {

	}
	
	public void playSoldierCard() {	
		
	}
	
	public void playRoadBuildingCard() {	
		
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		
	}

	/**
	 * This method is called whenever the observed object is changed. An application calls an
	 * Observable object's notifyObservers method to have all the object's observers notified of the change.
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(!init){
			state = new NotMyTurnState();
			initFromModel();
		}

		//if game started...

		if(Game.getInstance().getGameInfo() != null){
			if(isGameStarted() && (Game.getInstance().getGameInfo().getPlayers().size() == 4)){
				//set up states here
				doState();
				//update map
				updateCatanMap();
			}
		}
	}

	public void doState(){
		//check if the client's turn is the same as current player's turn, if so, do these
		TurnTracker turn = Game.getInstance().getTurnTracker();
		if(turn.getCurrentTurn() == Game.getInstance().getCurrentPlayerInfo().getPlayerIndex()){

			if(Game.getInstance().getTurnTracker().getStatus().equals("FirstRound")){
				//check if we've done the first round of the game
				if(!firstRoundDone){
					state = new FirstRoundState();

					if(settlementList.size() == 0){
						getView().startDrop(PieceType.SETTLEMENT, Game.getInstance().getCurrentPlayerInfo().getColor(), false);
					}
					else if(roadList.size() == 0){
						getView().startDrop(PieceType.ROAD, Game.getInstance().getCurrentPlayerInfo().getColor(), false);
					}

					//did we do it correctly?
					if(roadList.size() == 1 && settlementList.size() == 1){
						//we're done with the 1st round state
						firstRoundDone = true;
						state = new NotMyTurnState();
						return;
					}
				}

			}
			else if(Game.getInstance().getTurnTracker().getStatus().equals("SecondRound")){
//				check if we've done the second round of the game
				if (!secondRoundDone) {
					state = new SecondRoundState();

					if(settlementList.size() == 1){
						getView().startDrop(PieceType.SETTLEMENT, Game.getInstance().getCurrentPlayerInfo().getColor(), false);
					}
					else if(roadList.size() == 1)
						getView().startDrop(PieceType.ROAD, Game.getInstance().getCurrentPlayerInfo().getColor(), false);
					}

					//did we do it correctly?
					if(roadList.size() == 2 && settlementList.size() == 2) {
						//we're done with the 2nd round state
						secondRoundDone = true;
						state = new NotMyTurnState();
						return;
				}
			}
		}
		//it's not our turn, so set the state to NotMyTurnState
		else{
			state = new NotMyTurnState();
		}
	}

	private void updateCatanMap(){
		Game game = Game.getInstance();
		for(Building building : game.getMap().getBuildings()){
			//place settlements
			if(building instanceof Settlement){
				getView().placeSettlement(building.getLocation(), building.getColor());
			}
			//place cities
			else if(building instanceof City){
				getView().placeCity(building.getLocation(), building.getColor());
			}
		}

		//place roads
		for(Road road : game.getMap().getRoads()){
			getView().placeRoad(road.getLocation(), road.getColor());
		}

		//place robber
		getView().placeRobber(game.getMap().getRobber());
	}


	//this method puts water hexes to the map view on the borders of the catan map
	private void water(){
		//top to top-right corner
		getView().addHex(new HexLocation(0, -3), HexType.WATER);
		getView().addHex(new HexLocation(1, -3), HexType.WATER);
		getView().addHex(new HexLocation(2, -3), HexType.WATER);

		//top-right corner to bottom-right corner
		getView().addHex(new HexLocation(3, -3), HexType.WATER);
		getView().addHex(new HexLocation(3, -2), HexType.WATER);
		getView().addHex(new HexLocation(3, -1), HexType.WATER);

		//bottom-right corner to bottom
		getView().addHex(new HexLocation(3, 0), HexType.WATER);
		getView().addHex(new HexLocation(2, 1), HexType.WATER);
		getView().addHex(new HexLocation(1, 2), HexType.WATER);

		//bottom to bottom-left corner
		getView().addHex(new HexLocation(0, 3), HexType.WATER);
		getView().addHex(new HexLocation(-1, 3), HexType.WATER);
		getView().addHex(new HexLocation(-2, 3), HexType.WATER);

		//bottom-left to top-left corner
		getView().addHex(new HexLocation(-3, 3), HexType.WATER);
		getView().addHex(new HexLocation(-3, 2), HexType.WATER);
		getView().addHex(new HexLocation(-3, 1), HexType.WATER);

		//top-left corner to top
		getView().addHex(new HexLocation(-3, 0), HexType.WATER);
		getView().addHex(new HexLocation(-2, -1), HexType.WATER);
		getView().addHex(new HexLocation(-1, -2), HexType.WATER);
	}

	public static IGameState getState() {
		return state;
	}

	public static void setState(IGameState state) {
		MapController.state = state;
	}

	public static boolean isGameStarted() {
		return gameStarted;
	}

	public static void setGameStarted(boolean gameStarted) {
		MapController.gameStarted = gameStarted;
	}

	public static String directionToString(String direction){
		String returnString = "";
		switch(direction) {

			case "North":
				returnString = "N";
				break;

			case "West" :
				returnString = "W";
				break;

			case "South" :
				returnString = "S";
				break;

			case "East" :
				returnString = "E";
				break;

			case "NorthEast":
				returnString = "NE";
				break;

			case "NorthWest":
				returnString = "NW";
				break;

			case "SouthEast":
				returnString = "SE";
				break;

			case "SouthWest":
				returnString = "SW";
				break;

		}
		return returnString;
	}
}

