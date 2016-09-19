package model;

/**
 * The cost in resources of a object. If a player does not have all of these costs satisfied, then they may not buy the object.
 * Also used for trading as a convenience
 *
 * Created by kcwillmore on 9/17/16.
 */
public class Cost {

    private int brickCost;
    private int oreCost;
    private int sheepCost;
    private int wheatCost;
    private int woodCost;

    /**
     * Cost constructor, given the different values of a cost. Each cost may be 0 or greater
     *
     * @param brickCost brick resource value
     * @param oreCost stone resource value
     * @param sheepCost wool resource value
     * @param wheatCost grain resource value
     * @param woodCost lumber resource value
     */
    public Cost(int brickCost, int oreCost, int sheepCost, int wheatCost, int woodCost) {
        this.brickCost = brickCost;
        this.oreCost = oreCost;
        this.sheepCost = sheepCost;
        this.wheatCost = wheatCost;
        this.woodCost = woodCost;
    }

    public int getBrickCost() {
        return brickCost;
    }

    public int getWheatCost() {
        return wheatCost;
    }

    public int getOreCost() {
        return oreCost;
    }

    public int getSheepCost() {
        return sheepCost;
    }

    public int getWoodCost() {
        return woodCost;
    }
}
