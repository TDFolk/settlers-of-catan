package model;

import shared.definitions.CatanColor;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

import java.util.List;

import model.development_cards.DevelopmentCard;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class Player {
    private CatanColor color;
    private List<Settlement> settlements;
    private List<Road> roads;
    private List<ResourceCard> resourceCards;
    private List<DevelopmentCard> developmentCards;
    
    public Player() {
    	
    }
    
    
    
    public boolean canBuyRoad() {
    	return false;
    }
    
    public boolean canBuySettlement() {
    	return false;
    }
    
    public boolean canBuyCity() {
    	return false;
    }
    
    public boolean canBuyDevelopmentCard() {
    	return false;
    }

    public boolean canPlaceSettlement(VertexLocation vertex) {
    	return false;
    }
    
    public boolean canPlaceRoad(EdgeLocation edge) {
    	return false;
    }
    
    
}
