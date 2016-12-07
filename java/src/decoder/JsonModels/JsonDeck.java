package decoder.JsonModels;

import java.util.ArrayList;

/**
 * Created by bvanc on 9/30/2016.
 */
public class JsonDeck {

//    private ArrayList<Integer> deckList;
    public int getYearOfPlenty() {
        return yearOfPlenty;
    }

    public void setYearOfPlenty(int yearOfPlenty) {
        this.yearOfPlenty = yearOfPlenty;
    }

    public int getMonopoly() {
        return monopoly;
    }

    public void setMonopoly(int monopoly) {
        this.monopoly = monopoly;
    }

    public int getSoldier() {
        return soldier;
    }

    public void setSoldier(int soldier) {
        this.soldier = soldier;
    }

    public int getRoadBuilding() {
        return roadBuilding;
    }

    public void setRoadBuilding(int roadBuilding) {
        this.roadBuilding = roadBuilding;
    }

    public int getMonument() {
        return monument;
    }

    public void setMonument(int monument) {
        this.monument = monument;
    }

    public JsonDeck(int yearOfPlenty, int monopoly, int soldier, int roadBuilding, int monument)
    {
        this.yearOfPlenty = yearOfPlenty;
        this.monopoly = monopoly;
        this.soldier = soldier;
        this.roadBuilding = roadBuilding;
        this.monument = monument;

    }

    private int yearOfPlenty;
    private int monopoly;
    private int soldier;
    private int roadBuilding;
    private int monument;

    public int leftInDeck(){
        int amount = 0;
        amount += yearOfPlenty;
        amount += monopoly;
        amount += roadBuilding;
        amount += monument;
        amount += soldier;

        return amount;
    }

    public void decrementYearOfPlenty(){
        yearOfPlenty--;
    }

    public void decrementMonopoly(){
        monopoly--;
    }

    public void decrementSoldier(){
        soldier--;
    }

    public void decrementRoadBuilding(){
        roadBuilding--;
    }

    public void decrementMonument(){
        monument--;
    }
}
