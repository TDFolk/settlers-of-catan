package command.game;

import command.player.PlayerObject;

import java.util.List;

/**
 * Created by jihoon on 9/30/2016.
 */
public class GameListObject {
    private String title;
    private int id;
    private List<PlayerObject> playerList[];

    private GameListObject gameList[];

    public GameListObject(){

    }

    public GameListObject(String title, int id, List<PlayerObject> playerList[]) {
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

    public List<PlayerObject>[] getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<PlayerObject>[] playerList) {
        this.playerList = playerList;
    }

    public GameListObject[] getGameList() {
        return gameList;
    }

    public void setGameList(GameListObject[] gameList) {
        this.gameList = gameList;
    }
}
