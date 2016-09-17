package model;

/**
 * The cost in resources of a object.
 *
 * Created by kcwillmore on 9/17/16.
 */
public class Cost {

    int brickCost;
    int grainCost;
    int oreCost;
    int sheepCost;
    int woodCost;

    public Cost(int brickCost, int grainCost, int oreCost, int sheepCost, int woodCost) {
        this.brickCost = brickCost;
        this.grainCost = grainCost;
        this.oreCost = oreCost;
        this.sheepCost = sheepCost;
        this.woodCost = woodCost;
    }

    public int getBrickCost() {
        return brickCost;
    }

    public int getGrainCost() {
        return grainCost;
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
