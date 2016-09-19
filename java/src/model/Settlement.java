package model;

import shared.locations.VertexLocation;

/**
 * Created by kcwillmore on 9/17/16.
 */
public class Settlement {

    private static final Cost cost = new Cost(1,0,1,1,1);

    private VertexLocation location;

    public boolean canPlace() {
        return false;
    }
}
