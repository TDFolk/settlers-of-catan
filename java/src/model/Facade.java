package model;

import decoder.Decoder;
import decoder.JsonModels.JsonModel;
import model.cards_resources.ResourceCards;
import model.map.Hex;
import model.map.Port;
import model.map.ResourcePort;
import server.ServerProxy;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

import java.util.List;
import java.util.Observable;


/**
 * Facade class that stands between the Game and all other components. Anything that would access or change anything
 * about the model MUST go through this class.
 *
 * Created by Brandon on 9/17/16.
 */
public class Facade {
    private static Facade instance = null;

    private Facade() {
    }

    /**
     * Gets the current instance of the facade
     * @return current instance of the facade
     */
    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    /**
     * Logs the user into the game server
     *
     * @param username user's already registered name
     * @param password user's already registered password
     * @return the success or failure of the login
     */
    public boolean userLogin(String username, String password)
    {
        return ServerProxy.getServer().userLogin(username, password);
    }

    /**
     * Registers a user to the game server, must be completed before the user can log in
     *
     * @param username user's chosen name
     * @param password user's chosen password
     * @return success or failure of the register
     */
    public boolean userRegister(String username, String password)
    {
        return ServerProxy.getServer().userRegister(username, password);
    }


    public void initializeData()
    {
        String result = ServerProxy.getServer().gameModelVersion(Game.getInstance().getVersionNumber());
        if(!result.equals("\"true\"")) {
            replaceModel(result);
        }
    }

    /**
     *
     * @return the current client version number
     */
    public int getVersionNumber()
    {
        return Game.getInstance().getVersionNumber();
    }

    /**
     * Updates the client version model by one
     */
    public void incrementVersionNumber()
    {
        Game.getInstance().incrementVersionNumber();
    }

    public boolean canDrawResourceCard(ResourceType resourceType)
    {
        return Game.getInstance().getBank().canDrawResourceCard(resourceType);
    }

    public boolean canDrawDevelopmentCard()
    {
        return Game.getInstance().getBank().canDrawDevelopmentCard();
    }

    public boolean canBuyRoad()
    {
        return Game.getInstance().getPlayer().canBuyRoad();
    }

    public boolean canBuySettlement()
    {
        return Game.getInstance().getPlayer().canBuySettlement();
    }

    public boolean canBuyCity()
    {
        return Game.getInstance().getPlayer().canBuyCity();
    }

    public boolean canBuyDevelopmentCard()
    {
        return Game.getInstance().getPlayer().canBuyDevelopmentCard();
    }

    public boolean canPlayDevelopmentCards()
    {
        return Game.getInstance().getPlayer().canPlayDevelopmentCards();
    }

    public boolean canDiscard(ResourceCards cards)
    {
        //return Game.getInstance().getUser().canDiscard(cards);
        return true;
    }

    public boolean canPlaceSettlement(VertexLocation vertex)
    {
        return Game.getInstance().getPlayer().canPlaceSettlement(vertex);
    }

    public boolean canPlaceRoad(EdgeLocation edge)
    {
        return Game.getInstance().getPlayer().canPlaceRoad(edge);
    }

    public boolean canMakeTrade(ResourceCards offer)
    {
        return Game.getInstance().getPlayer().canMakeTrade(offer);
    }

    /**
     * Determines if the player has access to and sufficient resources for a maritime trade of the specified port type.
     *
     * @param resource Resource for 2:1 trades, set resource to null for 3:1 trading
     * @return true if the player may make a maritime trade of that type
     */
    public boolean canPortTrade(ResourceType resource)
    {
        return Game.getInstance().getPlayer().canPortTrade(resource);
    }

    /**
     * Determines if the player has access to any ports at all
     * @return true if the player has access to a port
     */
    public boolean canPortTrade() {
        for (Port port : Game.getInstance().getMap().getPorts()) {
            if (port.canTrade(Game.getInstance().getPlayer())){
                return true;
            }
        }
        return false;
    }

    /**
     * Replaces the old model with the new model returned from the server
     * @param newModelJson the new model returned from the server
     */
    public void replaceModel(String newModelJson) {

        JsonModel newModel = Decoder.getInstance().parseJson(newModelJson);

        Game.getInstance().replaceModel(newModel);


    }

    public int getPlayerResource(ResourceType resource){
        return Game.getInstance().getPlayer().getResourceCards().getResource(resource);
    }

    public int getPlayerRoads() {
        return Game.getInstance().getPlayer().getRoads();
    }

    public int getPlayerCities() {
        return Game.getInstance().getPlayer().getCities();
    }

    public int getPlayerSettlements() {
        return Game.getInstance().getPlayer().getSettlements();
    }

    public int getPlayerSoldiers() {
        return Game.getInstance().getPlayer().getSoldiers();
    }

    public List<Hex> getHexes()
    {
        return Game.getInstance().getMap().getHexes();
    }

    public int getPlayerIndex() {
        return Game.getInstance().getPlayer().getPlayerInfo().getPlayerIndex();
    }
}
