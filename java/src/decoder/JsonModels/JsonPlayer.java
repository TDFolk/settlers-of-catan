package decoder.JsonModels;

/**
 * Created by bvanc on 10/3/2016.
 */
public class JsonPlayer {

    private JsonResource resources;
    private JsonDevCard oldDevCards;
    private JsonDevCard newDevCards;
    private int roads;
    private int cities;
    private int settlements;
    private int soldiers;
    private int victoryPoints;
    private int monuments;
    private boolean playedDevCard;
    private boolean discarded;
    private int playerID;
    private int playerIndex;
    private String name;
    private String color;

    public JsonPlayer(String color, String name, int id, int index){
        this.resources = new JsonResource(0, 0, 0, 0, 0);
        this.oldDevCards = new JsonDevCard(0, 0, 0, 0, 0);
        this.newDevCards = new JsonDevCard(0, 0, 0, 0, 0);
        this.roads = 15;
        this.cities = 4;
        this.settlements = 5;
        this.soldiers = 0;
        this.victoryPoints = 0;
        this.monuments = 0;
        this.playedDevCard = false;
        this.discarded = false;
        this.playerID = id;
        this.playerIndex = index;
        this.name = name;
        this.color = color;


    }

    public JsonResource getResources() {
        return resources;
    }

    public void setResources(JsonResource resources) {
        this.resources = resources;
    }

    public JsonDevCard getOldDevCards() {
        return oldDevCards;
    }

    public void setOldDevCards(JsonDevCard oldDevCards) {
        this.oldDevCards = oldDevCards;
    }

    public JsonDevCard getNewDevCards() {
        return newDevCards;
    }

    public void setNewDevCards(JsonDevCard newDevCards) {
        this.newDevCards = newDevCards;
    }

    public int getRoads() {
        return roads;
    }

    public void setRoads(int roads) {
        this.roads = roads;
    }

    public int getCities() {
        return cities;
    }

    public void setCities(int cities) {
        this.cities = cities;
    }

    public int getSettlements() {
        return settlements;
    }

    public void setSettlements(int settlements) {
        this.settlements = settlements;
    }

    public int getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(int soldiers) {
        this.soldiers = soldiers;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getMonuments() {
        return monuments;
    }

    public void setMonuments(int monuments) {
        this.monuments = monuments;
    }

    public boolean isPlayedDevCard() {
        return playedDevCard;
    }

    public void setPlayedDevCard(boolean playedDevCard) {
        this.playedDevCard = playedDevCard;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void buyRoad() {
        resources.setBrick(resources.getBrick() - 1);
        resources.setWood(resources.getWood() - 1);
        roads--;
    }

    public void decrementRoadTotal() {
        roads--;
    }

    public void buySettlement() {
        resources.setBrick(resources.getBrick() - 1);
        resources.setSheep(resources.getSheep() - 1);
        resources.setWood(resources.getWood() - 1);
        resources.setWheat(resources.getWheat() - 1);
        settlements--;
    }

    public void decrementSettlement() {
        settlements--;
    }

    public void buyCity() {
        resources.setWheat(resources.getWheat() - 2);
        resources.setOre(resources.getOre() - 3);
        cities--;
        settlements++;
    }

    public void incrementPoints(){
        victoryPoints++;
    }

    public void addResources(JsonResource resourcesToAdd) {
        resources.setBrick(resources.getBrick() + resourcesToAdd.getBrick());
        resources.setOre(resources.getOre() + resourcesToAdd.getOre());
        resources.setSheep(resources.getSheep() + resourcesToAdd.getSheep());
        resources.setWheat(resources.getWheat() + resourcesToAdd.getWheat());
        resources.setWood(resources.getWood() + resourcesToAdd.getWood());
    }

    public boolean checkResourceAmount(){
        int amount = 0;
        amount += resources.getBrick();
        amount += resources.getOre();
        amount += resources.getSheep();
        amount += resources.getWheat();
        amount += resources.getWood();

        if(amount >= 7){
            return true;
        }
        else {
            return false;
        }
    }
}