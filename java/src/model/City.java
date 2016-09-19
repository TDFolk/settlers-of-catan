package model;

import shared.locations.VertexLocation;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class City {
    private static final Cost cost = new Cost(0,3,0,2,0);

    private VertexLocation location;

    public boolean canUpgradeSettlement() {
        return false;
    }
}
