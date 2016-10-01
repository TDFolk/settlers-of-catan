package command.player;

import com.google.gson.Gson;

/**
 * Created by jihoon on 9/30/2016.
 */
public class AcceptTradeObject {
    private final String type = "acceptTrade";
    private int playerIndex;
    private boolean willAccept;

    public AcceptTradeObject(int playerIndex, boolean willAccept){
        this.playerIndex = playerIndex;
        this.willAccept = willAccept;
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

    public boolean isWillAccept() {
        return willAccept;
    }

    public void setWillAccept(boolean willAccept) {
        this.willAccept = willAccept;
    }
}
