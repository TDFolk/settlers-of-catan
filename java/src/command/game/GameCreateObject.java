package command.game;

import com.google.gson.Gson;
import command.player.PlayerObject;

/**
 * Created by jihoon on 9/30/2016.
 */
public class GameCreateObject {

    private boolean randomTiles;
    private boolean randomNumbers;
    private boolean randomPorts;
    private String name;

    public GameCreateObject(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name){
        this.randomTiles = randomTiles;
        this.randomNumbers = randomNumbers;
        this.randomPorts = randomPorts;
        this.name = name;
    }

    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public PlayerObject[] getPlayers() {
//        return players;
//    }
//
//    public void setPlayers(PlayerObject[] players) {
//        this.players = players;
//    }

    public boolean isRandomTiles() {
        return randomTiles;
    }

    public void setRandomTiles(boolean randomTiles) {
        this.randomTiles = randomTiles;
    }

    public boolean isRandomNumbers() {
        return randomNumbers;
    }

    public void setRandomNumbers(boolean randomNumbers) {
        this.randomNumbers = randomNumbers;
    }

    public boolean isRandomPorts() {
        return randomPorts;
    }

    public void setRandomPorts(boolean randomPorts) {
        this.randomPorts = randomPorts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
