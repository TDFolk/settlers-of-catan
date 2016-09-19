package model;

import shared.locations.VertexLocation;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class City extends Building {
    private static final Cost cost = new Cost(0,3,0,2,0);

    private VertexLocation location;
    
    public void upgradeSettlement() {
    	
    }

    public boolean canUpgradeSettlement() {
        return false;
    }
}
