package decoder.JsonModels;

/**
 * Created by bvanc on 10/3/2016.
 */
public class JsonResource {

    private int brick;
    private int wood;
    private int sheep;
    private int wheat;
    private int ore;

    public JsonResource(int brick, int wood, int sheep, int wheat, int ore) {
        this.brick = brick;
        this.wood = wood;
        this.sheep = sheep;
        this.wheat = wheat;
        this.ore = ore;
    }

    public JsonResource(JsonResource playerResources, JsonResource tradeOffer, boolean isSender)
    {
        //a trade offer with negative numbers is what the sender is offering, the positive numbers are what they are asking for
        if(isSender)
        {
            this.brick = playerResources.getBrick() - tradeOffer.getBrick();
            this.wood = playerResources.getWood() - tradeOffer.getWood();
            this.sheep = playerResources.getSheep() - tradeOffer.getSheep();
            this.wheat = playerResources.getWheat() - tradeOffer.getWheat();
            this.ore = playerResources.getOre() - tradeOffer.getOre();
        }
        else
        {
            this.brick = playerResources.getBrick() + tradeOffer.getBrick();
            this.wood = playerResources.getWood() + tradeOffer.getWood();
            this.sheep = playerResources.getSheep() + tradeOffer.getSheep();
            this.wheat = playerResources.getWheat() + tradeOffer.getWheat();
            this.ore = playerResources.getOre() + tradeOffer.getOre();
        }


    }

    public int getBrick() {
        return brick;
    }

    public void setBrick(int brick) {
        this.brick = brick;
    }

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public int getSheep() {
        return sheep;
    }

    public void setSheep(int sheep) {
        this.sheep = sheep;
    }

    public int getWheat() {
        return wheat;
    }

    public void setWheat(int wheat) {
        this.wheat = wheat;
    }

    public int getOre() {
        return ore;
    }

    public void setOre(int ore) {
        this.ore = ore;
    }
}
