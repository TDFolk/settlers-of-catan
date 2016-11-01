package client.map;

import java.util.*;

import client.states.*;
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
	//private EdgeLocation firstRoad;
	private static HexLocation robberLocation;
	private boolean init = false;
	//????
	//RobPlayerInfo[] empty = {};

	private boolean firstRoundDone = false;
	private boolean secondRoundDone = false;

	//need to get this info from the server..........
	private List<Road> roadList = new ArrayList<>();
	private List<Building> settlementList = new ArrayList<>();
	private List<Building> cityList = new ArrayList<>();

	private static Building secondBuilding;

//	private boolean checkFirst = false;
//	private boolean checkSecond = false;

	private boolean playingSoldier = false;
	private boolean playingRoadBuilding = false;
	
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
						robberLocation = Game.getInstance().getMap().getRobber();
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
		return Facade.getInstance().canPlaceRoad(edgeLoc);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return Facade.getInstance().canPlaceSettlement(vertLoc);
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		return Facade.getInstance().canPlaceCity(vertLoc);
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		if(hexLoc.getX() == robberLocation.getX() && hexLoc.getY() == robberLocation.getY()){
			return false;
		}
		if(Facade.getInstance().getHexAtLocation(hexLoc).getHexType() == HexType.WATER){
			return false;
		}
		return true;
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		getView().placeRoad(edgeLoc, Game.getInstance().getCurrentPlayerInfo().getColor());
		if(state instanceof FirstRoundState || state instanceof SecondRoundState){
			ServerProxy.getServer().buildRoad(Game.getInstance().getCurrentPlayerInfo().getPlayerIndex(), edgeLoc, true);
			roadList.add(new Road(Game.getInstance().getCurrentPlayerInfo().getColor(), edgeLoc));


			//is this the right place to finish the turn????
			ServerProxy.getServer().finishTurn(Game.getInstance().getCurrentPlayerInfo().getPlayerIndex());
			if(state instanceof SecondRoundState){
				//state = new RollingState();
			}
		}
		else {
			ServerProxy.getServer().buildRoad(Game.getInstance().getCurrentPlayerInfo().getPlayerIndex(), edgeLoc, false);
		}
	}

	public void placeSettlement(VertexLocation vertLoc) {
		getView().placeSettlement(vertLoc, Game.getInstance().getCurrentPlayerInfo().getColor());
		if(state instanceof FirstRoundState || state instanceof SecondRoundState){
			ServerProxy.getServer().buildSettlement(Game.getInstance().getCurrentPlayerInfo().getPlayerIndex(), vertLoc, true);
			settlementList.add(new Settlement(Game.getInstance().getCurrentPlayerInfo().getColor(), vertLoc));
			if (state instanceof  SecondRoundState) {
				secondBuilding = new Settlement(Game.getInstance().getCurrentPlayerInfo().getColor(), vertLoc);
			}
		}
		else {
			ServerProxy.getServer().buildSettlement(Game.getInstance().getCurrentPlayerInfo().getPlayerIndex(), vertLoc, false);
		}
	}

	public void placeCity(VertexLocation vertLoc) {
		
		getView().placeCity(vertLoc, Game.getInstance().getCurrentPlayerInfo().getColor());
		ServerProxy.getServer().buildCity(Game.getInstance().getCurrentPlayerInfo().getPlayerIndex(), vertLoc);

	}

	public void placeRobber(HexLocation hexLoc) {
		if(getRobView().isModalShowing()){
			getRobView().closeModal();
		}

		getRobView().setPlayers(null);
		getView().placeRobber(hexLoc);

		ArrayList<RobPlayerInfo> robPlayerInfoArrayList = new ArrayList<>();
		int index = Game.getInstance().getCurrentPlayerInfo().getPlayerIndex();

		for(int i = 0; i < 4; i++){
			if(i != index){
				
			}
		}


		getRobView().showModal();
		robberLocation = hexLoc;
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, Game.getInstance().getCurrentPlayerInfo().getColor(), true);
	}
	
	public void cancelMove() {
		playingRoadBuilding = false;
		playingSoldier = false;
		if(!(state instanceof PlayingState)){
			state = new PlayingState();
		}
	}
	
	public void playSoldierCard() {
		playingSoldier = true;
		state = new RobbingState();
		getView().startDrop(PieceType.ROBBER, Game.getInstance().getCurrentPlayerInfo().getColor(), true);
	}

	
	public void playRoadBuildingCard() {
		playingRoadBuilding = true;

		int index = Game.getInstance().getCurrentPlayerInfo().getPlayerIndex();
		if(Game.getInstance().getPlayersList().get(index).getRoads() == 0){
			//no more roads to build....
			return;
		}
		getView().startDrop(PieceType.ROAD, Game.getInstance().getCurrentPlayerInfo().getColor(), true);
	}
	
	public void robPlayer(RobPlayerInfo victim) {

		//this will check if the soldier card was played
		if(playingSoldier){
			playingSoldier = false;
			if(victim == null){
				//do something here
				ServerProxy.getServer().soldier(Game.getInstance().getCurrentPlayerInfo().getPlayerIndex(), -1, robberLocation);
			}
			else {
				ServerProxy.getServer().soldier(Game.getInstance().getCurrentPlayerInfo().getPlayerIndex(), victim.getPlayerIndex(), robberLocation);
			}
		}
		//this will be for if you roll a 7
		else {
			if(victim == null){
				ServerProxy.getServer().robPlayer(Game.getInstance().getCurrentPlayerInfo().getPlayerIndex(), -1, robberLocation);
			}
			else {
				ServerProxy.getServer().robPlayer(Game.getInstance().getCurrentPlayerInfo().getPlayerIndex(), victim.getPlayerIndex(), robberLocation);
			}
		}

		//.... is it showing?
		if(getRobView().isModalShowing()){
			getRobView().closeModal();
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
		String status = turn.getStatus();
		if(turn.getCurrentTurn() == Game.getInstance().getCurrentPlayerInfo().getPlayerIndex()){

			//is the server status in the first round?
			if(Game.getInstance().getTurnTracker().getStatus().equals("FirstRound")){
				checkBuildingList();

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
						//return;
					}
				}

			}
			//is the server status in the second round?
			else if(Game.getInstance().getTurnTracker().getStatus().equals("SecondRound")){

				//check if we've done the second round of the game
				if (!secondRoundDone) {
					//checkBuildingList();
					state = new SecondRoundState();
					if(settlementList.size() == 1){
						getView().startDrop(PieceType.SETTLEMENT, Game.getInstance().getCurrentPlayerInfo().getColor(), false);


					}
					else if(roadList.size() == 1){
						getView().startDrop(PieceType.ROAD, Game.getInstance().getCurrentPlayerInfo().getColor(), false);
					}

					//did we do it correctly?
					if(roadList.size() == 2 && settlementList.size() == 2) {
						//we're done with the 2nd round state
						secondRoundDone = true;
						//state = new NotMyTurnState();
						//return;
					}
				}
			}
			else if(Game.getInstance().getTurnTracker().getStatus().equals("Playing")){
				if(!(state instanceof PlayingState)){
					state = new PlayingState();
				}

			}
			else if(Game.getInstance().getTurnTracker().getStatus().equals("Robbing")){
				if(!(state instanceof RobbingState)){
					state = new RobbingState();
				}
				getView().startDrop(PieceType.ROBBER, Game.getInstance().getCurrentPlayerInfo().getColor(), false);
				//return;

			}
			else if(Game.getInstance().getTurnTracker().getStatus().equals("Discarding")){
				//do something
			}
			else if(Game.getInstance().getTurnTracker().getStatus().equals("Rolling")){
				//state = new RollingState();

			}
		}
		//it's not our turn, so set the state to NotMyTurnState
		else{
			state = new NotMyTurnState();
		}
	}

	//this method draws all the settlements, roads, buildings, and robbers
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

	//TODO: CHECK THE RE-JOIN...
	private void checkBuildingList(){
		if(Game.getInstance().getMap().getBuildings() != null){
			for(Building building : Game.getInstance().getMap().getBuildings()){
				if(building instanceof Settlement && building.getColor().equals(Game.getInstance().getCurrentPlayerInfo().getColor())){
					settlementList = new ArrayList<>();
					settlementList.add(building);
				}
				else if(building instanceof  City && building.getColor().equals(Game.getInstance().getCurrentPlayerInfo().getColor())){
					cityList = new ArrayList<>();
					cityList.add(building);
				}
			}
		}
		if(Game.getInstance().getMap().getRoads() != null){
			for(Road road : Game.getInstance().getMap().getRoads()){
				if(road.getColor().equals(Game.getInstance().getCurrentPlayerInfo().getColor())){
					roadList = new ArrayList<>();
					roadList.add(road);
				}
			}
		}
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

	public static Building getSecondBuilding() {
		return secondBuilding;
	}

	public static void setRobberLocation(HexLocation robberLocation) {
		MapController.robberLocation = robberLocation;
	}
}

