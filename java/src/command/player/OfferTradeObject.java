package command.player;

import model.cards_resources.ResourceCards;

/**
 * Created by jihoon on 9/30/2016.
 */
public class OfferTradeObject {
    private final String type = "offerTrade";
    private int playerIndex;
    private ResourceCards offer;
    private int receiver;

    public OfferTradeObject(int playerIndex, ResourceCards offer, int receiver) {
        this.playerIndex = playerIndex;
        this.offer = offer;
        this.receiver = receiver;
    }

    public String getType() {
        return type;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public ResourceCards getOffer() {
        return offer;
    }

    public void setOffer(ResourceCards offer) {
        this.offer = offer;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public String toJSON(){
        String response = "{\n" +
                "  \"type\": \"offerTrade\",\n" +
                "  \"playerIndex\": " + playerIndex + ",\n" +
                "  \"offer\": {\n" +
                "    \"brick\": " + offer.getBrick() +  ",\n" +
                "    \"ore\": " + offer.getOre() + ",\n" +
                "    \"sheep\": " + offer.getSheep() + " ,\n" +
                "    \"wheat\": " + offer.getWheat() + ",\n" +
                "    \"wood\": " + offer.getWood() + "\n" +
                "  },\n" +
                "  \"receiver\": " + receiver + "\n" +
                "}";
        return response;
    }
}
