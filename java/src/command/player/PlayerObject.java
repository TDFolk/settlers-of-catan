package command.player;

import shared.definitions.CatanColor;

/**
 * Created by jihoon on 9/30/2016.
 */
public class PlayerObject {

    private CatanColor color;
    private String name;
    private int id;

    public PlayerObject(CatanColor color, String name, int id){
        this.color = color;
        this.name = name;
        this.id = id;
    }

    public CatanColor getColor() {
        return color;
    }

    public void setColor(CatanColor color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
