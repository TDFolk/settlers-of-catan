package model;

import decoder.JsonModels.JsonModel;
import model.cards_resources.Bank;
import model.cards_resources.Trade;
import model.map.Map;

import java.util.List;

/**
 * Game class, contains collections of all model objects within the game
 * Singleton because there can Only Be One
 *
 * Created by kcwillmore on 9/17/16.
 */
public class Game {
    private static Game instance = null;

    private int versionNumber = 0;
    private Bank bank;
    private List<Message> chat;
    private List<Message> log;
    private Map map;
    private List<Player> players;
    private Player user;
    private Trade activeTrade; //null if none is ongoing
    private TurnTracker turntracker;
    private Player winner;

    private Game() {}

    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }
        return instance;
    }

    /**
     * Initialize/reinitialize all of the data in the model from the info returned by the server
     */
    public void initializeData(int versionNumber, Bank bank, List<Message> chat, List<Message> log, Map map,
                               List<Player> players, Player user, Trade activeTrade, TurnTracker turnTracker, Player winner) {
        this.versionNumber = versionNumber;
        this.bank = bank;
        this.chat = chat;
        this.log = log;
        this.map = map;
        this.players = players;
        this.user = user;
        this.activeTrade = activeTrade; //null if none is ongoing
        this.turntracker = turnTracker;
        this.winner = winner;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void incrementVersionNumber() {
        versionNumber++;
    }

    public Map getMap() {
        return map;
    }

    public Bank getBank(){return bank; }

    public List<Player> getPlayers(){return players; }

    public Player getUser(){return user;}


    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setUser(Player user) {
        this.user = user;
    }

    public void replaceModel(JsonModel model)
    {

    }
}
