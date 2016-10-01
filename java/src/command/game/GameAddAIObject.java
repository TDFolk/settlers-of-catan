package command.game;

import com.google.gson.Gson;

/**
 * Created by jihoon on 9/30/2016.
 */
public class GameAddAIObject {
    private String AIType;

    public GameAddAIObject(String AIType){
        this.AIType = AIType;
    }

    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getAIType() {
        return AIType;
    }

    public void setAIType(String AIType) {
        this.AIType = AIType;
    }
}
