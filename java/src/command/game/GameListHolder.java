package command.game;

import client.data.GameInfo;

/**
 * Created by jihoon on 10/24/2016.
 */
public class GameListHolder {
    private GameInfo[] gameInfos;
    private GameListObject[] gameListObjects;
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public GameListObject[] getGameListObjects() {
        return gameListObjects;
    }

    public void setGameListObjects(GameListObject[] gameListObjects) {
        this.gameListObjects = gameListObjects;
    }

    public GameInfo[] getGameInfos() {
        return gameInfos;
    }

    public void setGameInfos(GameInfo[] gameInfos) {
        this.gameInfos = gameInfos;
    }
}
