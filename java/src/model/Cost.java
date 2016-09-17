package model;

/**
 * The cost in resources of a object. If a player does not have all of these costs satisfied, then they may not buy the object.
 * Also used for trading as a convenient
 *
 * Created by kcwillmore on 9/17/16.
 */
public class Cost {

    int brickCost;
    int oreCost;
    int sheepCost;
    int wheatCost;
    int woodCost;

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
