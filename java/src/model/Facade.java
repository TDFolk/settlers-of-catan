package model;

import com.google.gson.JsonObject;
import model.development_cards.DevelopmentCard;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

import java.util.List;


/**
 * Facade class that stands between the Game and all other components. Anything that would access or change anything
 * about the model MUST go through this class.
 *
 * Created by kcwillmore on 9/17/16.
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
        return Game.getInstance().getUser().canBuyRoad();
    }

    public boolean canBuySettlement()
    {
        return Game.getInstance().getUser().canBuySettlement();
    }

    public boolean canBuyCity()
    {
        return Game.getInstance().getUser().canBuyCity();
    }

    public boolean canBuyDevelopmentCard()
    {
        return Game.getInstance().getUser().canBuyDevelopmentCard();
    }

    public boolean canPlayDevelopmentCard(DevelopmentCard card)
    {
        return Game.getInstance().getUser().canPlayDevelopmentCard(card);
    }

    public boolean canDiscard(List<ResourceCard> cards)
    {
        return Game.getInstance().getUser().canDiscard(cards);
    }

    public boolean canPlaceSettlement(VertexLocation vertex)
    {
        return Game.getInstance().getUser().canPlaceSettlement(vertex);
    }

    public boolean canPlaceRoad(EdgeLocation edge)
    {
        return Game.getInstance().getUser().canPlaceRoad(edge);
    }

    public boolean canMakeTrade(ResourceValues offer)
    {
        return Game.getInstance().getUser().canMakeTrade(offer);
    }

    public boolean canTrade()
    {
        //Where does the "trade" object get called from? Looks like we will have to add it to the Game Class..
        return false;
    }

    /**
     * Replaces the old model with the new model returned from the server
     * @param newModel the new model returned from the server
     */
    public void replaceModel(String newModel) {}
}
