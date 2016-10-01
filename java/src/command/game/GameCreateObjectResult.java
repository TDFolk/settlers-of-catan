package command.game;

import command.player.PlayerObject;

/**
 * Created by jihoon on 9/30/2016.
 */
public class GameCreateObjectResult {
    private String title;
    private int id;
    private PlayerObject[] players;

    public GameCreateObjectResult(){

    }

    public PlayerObject[] getPlayers() {
        return players;
    }

    public void setPlayers(PlayerObject[] players) {
        this.players = players;
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
}
