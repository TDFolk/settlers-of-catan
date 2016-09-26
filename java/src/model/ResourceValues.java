package model;

import exception.ResourceException;

/**
 * The value of each of the five Catan resources in an object.
 * Trading costs, bank values, cards in hand, all are just values of each of the five resources.
 * Useful for comparing costs for purchasing/trading.
 *
 * Created by kcwillmore on 9/17/16.
 */
public class ResourceValues {

    private int brick;
    private int ore;
    private int sheep;
    private int wheat;
    private int wood;

    /**
     * ResourceValues constructor, given the different values of a cost. Each cost may be 0 or greater
     *
     * @param brick brick resource value
     * @param ore stone resource value
     * @param sheep wool resource value
     * @param wheat grain resource value
     * @param wood lumber resource value
     */
    public ResourceValues(int brick, int ore, int sheep, int wheat, int wood) throws ResourceException{
        if (brick < 0 || ore < 0 || sheep < 0 || wheat < 0 || wood < 0) throw new ResourceException();
        this.brick = brick;
        this.ore = ore;
        this.sheep = sheep;
        this.wheat = wheat;
        this.wood = wood;
    }

    public int getBrick() {
        return brick;
    }

    public int getWheat() {
        return wheat;
    }

    public int getOre() {
        return ore;
    }

    public int getSheep() {
        return sheep;
    }

    public int getWood() {
        return wood;
    }

    /**
     * Compares this resource pool to the cost asked of it to see whether there is enough of every resource to pay the cost
     *
     * @param cost the resources being asked of this
     * @return true if this resourceValue has equal or greater than every value of the cost, false if any value is lesser
     */
    public boolean canPay(ResourceValues cost) {
        return brick >= cost.brick &&
                ore >= cost.ore &&
                sheep >= cost.sheep &&
                wheat >= cost.wheat &&
                wood >= cost.wood;
    }
}
