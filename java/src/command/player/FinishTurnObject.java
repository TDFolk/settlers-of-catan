package command.player;

import com.google.gson.Gson;

/**
 * Created by jihoon on 9/30/2016.
 */
public class FinishTurnObject {
    private final String type = "finishTurn";
    private int playerIndex;

    public FinishTurnObject(int playerIndex) {
        this.playerIndex = playerIndex;
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
}
