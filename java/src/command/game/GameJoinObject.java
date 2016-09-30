package command.game;

import com.google.gson.Gson;
import shared.definitions.CatanColor;

/**
 * Created by jihoon on 9/30/2016.
 */
public class GameJoinObject {
    private int id;
    private CatanColor color;

    public GameJoinObject(int id, CatanColor color){
        this.id = id;
        this.color = color;
    }

    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CatanColor getColor() {
        return color;
    }

    public void setColor(CatanColor color) {
        this.color = color;
    }
}
