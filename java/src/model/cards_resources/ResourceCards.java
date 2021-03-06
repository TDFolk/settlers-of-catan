package model.cards_resources;

import shared.definitions.ResourceType;

/**
 * The value of each of the five Catan resources in one object.
 * Trading costs, bank values, cards in hand, all are just values of each of the five resources.
 * Useful for comparing costs for purchasing/trading.
 *
 * Created by kcwillmore on 9/17/16.
 */
public class ResourceCards {

    private int brick;
    private int ore;
    private int sheep;
    private int wheat;
    private int wood;

    /**
     * ResourceCards constructor, given the different values of a cost. Each cost may be 0 or greater
     *
     * @pre All parameters must be > 0
     * @param brick brick resource value
     * @param ore stone resource value
     * @param sheep wool resource value
     * @param wheat grain resource value
     * @param wood lumber resource value
     */
    public ResourceCards(int brick, int ore, int sheep, int wheat, int wood) {
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
    public boolean canPay(ResourceCards cost) {
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

    public void reduceResources(ResourceCards cost) {
        brick -= cost.brick;
        ore -= cost.ore;
        sheep -= cost.sheep;
        wheat -= cost.wheat;
        wood -= cost.wheat;
    }

    public void increaseResources(ResourceCards cost) {
        brick += cost.brick;
        ore += cost.ore;
        sheep += cost.sheep;
        wheat += cost.wheat;
        wood += cost.wheat;
    }

    public void resetOneResource(ResourceType resource) {
        if (resource.equals(ResourceType.BRICK)) {
            this.brick = 0;
        }
        else if (resource.equals(ResourceType.ORE)) {
            this.ore = 0;
        }
        else if (resource.equals(ResourceType.SHEEP)) {
            this.sheep = 0;
        }
        else if (resource.equals(ResourceType.WHEAT)) {
            this.wheat = 0;
        }
        else {
            this.wood = 0;
        }
    }

    public void subtractOneResource (ResourceType resource) {
        if (resource.equals(ResourceType.BRICK)) {
            if (this.brick > 0) {
                this.brick--;
            }
        }
        else if (resource.equals(ResourceType.ORE)) {
            if (this.ore > 0) {
                this.ore--;
            }
        }
        else if (resource.equals(ResourceType.SHEEP)) {
            if (this.sheep > 0) {
                this.sheep--;
            }
        }
        else if (resource.equals(ResourceType.WHEAT)) {
            if (this.wheat > 0) {
                this.wheat--;
            }
        }
        else {
            if (this.wood > 0) {
                this.wood--;
            }
        }
    }

    public void addOneResource (ResourceType resource) {
        if (resource.equals(ResourceType.BRICK)) {
                this.brick++;
        }
        else if (resource.equals(ResourceType.ORE)) {
                this.ore++;
        }
        else if (resource.equals(ResourceType.SHEEP)) {
                this.sheep++;
        }
        else if (resource.equals(ResourceType.WHEAT)) {
                this.wheat++;
        }
        else {
                this.wood++;
        }
    }
}
