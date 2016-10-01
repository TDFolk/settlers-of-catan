package command.game;

import command.player.PlayerObject;

/**
 * Created by jihoon on 9/30/2016.
 */
public class GameListObjectResult {
    private GameListObject[] gameList;

    public GameListObjectResult(){

    }

    public GameListObject[] getGameList() {
        return gameList;
    }

    public void setGameList(GameListObject[] gameList) {
        this.gameList = gameList;
    }
}
