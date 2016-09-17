package model.development_cards;

import model.Cost;

/**
 * Created by kcwillmore on 9/17/16.
 */
public abstract class DevelopmentCard {

    private static final Cost cost = new Cost(0,1,1,1,0);
    
    public abstract void doAction();
    
    
    
    
}
