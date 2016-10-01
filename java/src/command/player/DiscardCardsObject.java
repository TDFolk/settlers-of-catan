package command.player;

import com.google.gson.Gson;
import model.cards_resources.ResourceCards;

/**
 * Created by jihoon on 9/30/2016.
 */
public class DiscardCardsObject {
    private final String type = "discardCards";
    private int playerIndex;
    private ResourceCards discardedCards;

    public DiscardCardsObject(int playerIndex, ResourceCards discardedCards){
        this.playerIndex = playerIndex;
        this.discardedCards = discardedCards;
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

    public ResourceCards getDiscardedCards() {
        return discardedCards;
    }

    public void setDiscardedCards(ResourceCards discardedCards) {
        this.discardedCards = discardedCards;
    }

    public String toJSON(){
//        Gson gson = new Gson();
//        return gson.toJson(this);
        return "{" +
                "\"type\": " + "\"" + type + "\"," +
                "\"playerIndex\": " + "\"" + playerIndex + "\"," +
                "\"discardedCards\": {" +
                "\"brick\": " + "\"" + discardedCards.getBrick() + "\"," +
                "\"ore\": " + "\"" + discardedCards.getOre() + "\"," +
                "\"sheep\": " + "\"" + discardedCards.getSheep() + "\"," +
                "\"wheat\": " + "\"" + discardedCards.getWheat() + "\"," +
                "\"wood\": " + "\"" + discardedCards.getWood() + "\"}" +
                "}";

    }
}
