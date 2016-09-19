package model;

import shared.locations.EdgeLocation;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class Road {

    private static final Cost cost = new Cost(1,0,0,0,1);

    private EdgeLocation location;

    public boolean canPlace() {
        return false;
    }
}
