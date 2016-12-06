package decoder.JsonModels;

import model.cards_resources.ResourceCards;

/**
 * Created by bvanc on 10/25/2016.
 */
public class JsonTradeOffer {
    private int sender;
    private int receiver;
    private JsonResource offer;

    public JsonTradeOffer(int sender, ResourceCards offer, int receiver)
    {
        this.sender = sender;
        this.offer = new JsonResource(offer.getBrick(), offer.getWood(), offer.getSheep(), offer.getWheat(), offer.getOre());
        this.receiver = receiver;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public JsonResource getOffer() {
        return offer;
    }

    public void setOffer(JsonResource offer) {
        this.offer = offer;
    }
}
