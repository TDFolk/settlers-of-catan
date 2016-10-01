package command.game;

import command.player.PlayerObject;

import java.util.List;

/**
 * Created by jihoon on 9/30/2016.
 */
public class GameListObject {
    private String title;
    private int id;
    private PlayerObject playerList[];

    public GameListObject(){

    }

    public GameListObject(String title, int id, PlayerObject playerList[]) {
        this.title = title;
        this.id = id;
        this.playerList = playerList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlayerObject[] getPlayerList() {
        return playerList;
    }

    public void setPlayerList(PlayerObject[] playerList) {
        this.playerList = playerList;
    }
}
