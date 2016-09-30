package model;

import shared.definitions.ResourceType;

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
     * @pre All parameters must be > 0
     * @param brick brick resource value
     * @param ore stone resource value
     * @param sheep wool resource value
     * @param wheat grain resource value
     * @param wood lumber resource value
     */
    public ResourceValues(int brick, int ore, int sheep, int wheat, int wood) {
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
    
    public int getResource(ResourceType resource) {
    	switch (resource) {
    	case BRICK: return brick;
    	case ORE: return ore;
    	case SHEEP: return sheep;
    	case WHEAT: return wheat;
    	case WOOD: return wood;
    	}
    	return 0;
    }

    public void setBrick(int brick) {
        this.brick = brick;
    }

    public void setOre(int ore) {
        this.ore = ore;
    }

    public void setSheep(int sheep) {
        this.sheep = sheep;
    }

    public void setWheat(int wheat) {
        this.wheat = wheat;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }
    
    public void setResource(ResourceType resource, int num) {
    	switch (resource) {
    	case BRICK: brick = num; break;
    	case ORE: ore = num; break;
    	case SHEEP: sheep = num; break;
    	case WHEAT: wheat = num; break;
    	case WOOD: wood = num; break;
    	}
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

    /**
     * Gets the sum total of all different resources in this values
     *
     * @return sum of the five resources
     */
    public int size() {
        return brick + ore + sheep + wheat + wood;
    }
}
