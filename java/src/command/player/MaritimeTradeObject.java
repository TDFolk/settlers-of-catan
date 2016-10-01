package command.player;

import com.google.gson.Gson;

/**
 * Created by jihoon on 9/30/2016.
 */
public class MaritimeTradeObject {
    private final String type = "maritimeTrade";
    private int playerIndex;
    private int ratio;
    private String inputResource;
    private String outputResource;

    public MaritimeTradeObject(int playerIndex, int ratio, String inputResource, String outputResource) {
        this.playerIndex = playerIndex;
        this.ratio = ratio;
        this.inputResource = inputResource;
        this.outputResource = outputResource;
    }

    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
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

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public String getInputResource() {
        return inputResource;
    }

    public void setInputResource(String inputResource) {
        this.inputResource = inputResource;
    }

    public String getOutputResource() {
        return outputResource;
    }

    public void setOutputResource(String outputResource) {
        this.outputResource = outputResource;
    }
}
