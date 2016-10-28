package client.map;

import java.util.*;

import client.states.FirstRoundState;
import client.states.IGameState;
import client.states.NotMyTurnState;
import model.Game;
import model.map.*;
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

//		Random rand = new Random();
//
//		for (int x = 0; x <= 3; ++x) {
//
//			int maxY = 3 - x;
//			for (int y = -3; y <= maxY; ++y) {
//				int r = rand.nextInt(HexType.values().length);
//				HexType hexType = HexType.values()[r];
//				if(hexType == HexType.WATER){
//					hexType = HexType.ORE;
//				}
//				HexLocation hexLoc = new HexLocation(x, y);
//				getView().addHex(hexLoc, hexType);
////				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
////						CatanColor.RED);
////				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
////						CatanColor.BLUE);
////				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
////						CatanColor.ORANGE);
////				getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
////				getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
//			}
//
//			if (x != 0) {
//				int minY = x - 3;
//				for (int y = minY; y <= 3; ++y) {
//					int r = rand.nextInt(HexType.values().length);
//					HexType hexType = HexType.values()[r];
//					if(hexType == HexType.WATER){
//						hexType = HexType.ORE;
//					}
//					HexLocation hexLoc = new HexLocation(-x, y);
//					getView().addHex(hexLoc, hexType);
////					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
////							CatanColor.RED);
////					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
////							CatanColor.BLUE);
////					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
////							CatanColor.ORANGE);
////					getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
////					getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
//				}
//			}
//		}
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
		
		getView().placeRoad(edgeLoc, CatanColor.ORANGE);
	}

	public void placeSettlement(VertexLocation vertLoc) {
		
		getView().placeSettlement(vertLoc, CatanColor.ORANGE);
	}

	public void placeCity(VertexLocation vertLoc) {
		
		getView().placeCity(vertLoc, CatanColor.ORANGE);
	}

	public void placeRobber(HexLocation hexLoc) {
		
		getView().placeRobber(hexLoc);
		
		getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		getView().startDrop(pieceType, CatanColor.ORANGE, true);
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

	private void resetView(){
//		getView().reset();
		state = new NotMyTurnState();
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
}

