package command.game;

import com.google.gson.Gson;
import shared.definitions.CatanColor;

/**
 * Created by jihoon on 9/30/2016.
 */
public class GameJoinObject {
    private int id;
    private String color;

    public GameJoinObject(int id, String color){
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
