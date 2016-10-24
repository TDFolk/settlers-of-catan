package model;

import client.states.IGameState;
import decoder.JsonModels.JsonDeck;
import decoder.JsonModels.JsonLine;
import decoder.JsonModels.JsonModel;
import model.cards_resources.Bank;
import model.cards_resources.DevelopmentCard;
import model.cards_resources.ResourceCards;
import model.cards_resources.Trade;
import model.map.Map;
import server.ServerProxy;
import shared.definitions.DevCardType;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Game class, contains collections of all model objects within the game
 * Singleton because there can Only Be One
 * Extends Observable to notify Controllers(Observers) when there has been a change to the model
 *
 *
 */
public class Game extends Observable {
    private static Game instance = null;

    private static ServerProxy server = null;

    private int versionNumber = 0;
    private Bank bank;
    private List<Message> chat;
    private List<Message> log;
    private Map map;
    private List<Player> playersList;
    private Player player;
    private Trade activeTrade; //null if none is ongoing
    private TurnTracker turntracker;
    private Player winner;
    private IGameState gameState;


    public void replaceModel(JsonModel model)
    {
        //Version Number
        this.versionNumber = model.getVersion();

        //NEW BANK
        ArrayList<DevelopmentCard> deck = createDeck(model);
        ResourceCards pool = new ResourceCards(model.getBank().getBrick(),
                                                model.getBank().getOre(),
                                                model.getBank().getSheep(),
                                                model.getBank().getWheat(),
                                                model.getBank().getWood());
        this.bank = new Bank(pool, deck);

        ArrayList<Message> chat = createMessageList(model.getChat().getLines());
        ArrayList<Message> log = createMessageList(model.getLog().getLines());

        this.log = log;
        this.chat = chat;

        //TODO: Finish implementing this method to fill the map data member and below.



        // Marks this Observable object as having been changed; the hasChanged method will now return true.
        this.setChanged();
        // If this object has changed, as indicated by the hasChanged method, then notify all of
        // its observers and then call the clearChanged method to indicate that this object has no longer changed.
        this.notifyObservers(gameState);

    }

    public ArrayList<Message> createMessageList(JsonLine[] lines)
    {

        ArrayList<Message> messageList = new ArrayList<>();

        for(int i = 0; i < lines.length; i++)
        {
            messageList.add(new Message(lines[i].getMessage(), lines[i].getSource()));
        }

        return messageList;

    }

    public ArrayList<DevelopmentCard> createDeck(JsonModel model)
    {
        JsonDeck newDeck = model.getDeck();

        ArrayList<DevelopmentCard> deck = new ArrayList<>();

        for(int i = 0; i < newDeck.getMonopoly(); i++)
        {
            deck.add(new DevelopmentCard(DevCardType.MONOPOLY));
        }

        for(int i = 0; i < newDeck.getMonument(); i++)
        {
            deck.add(new DevelopmentCard(DevCardType.MONUMENT));
        }

        for(int i = 0; i < newDeck.getRoadBuilding(); i++)
        {
            deck.add(new DevelopmentCard(DevCardType.ROAD_BUILD));
        }

        for(int i = 0; i < newDeck.getSoldier(); i++)
        {
            deck.add(new DevelopmentCard(DevCardType.SOLDIER));
        }

        for (int i = 0; i < newDeck.getYearOfPlenty(); i++)
        {
            deck.add(new DevelopmentCard(DevCardType.YEAR_OF_PLENTY));
        }

        return deck;
    }

    private Game() {}

    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
            server = new ServerProxy();
        }
        return instance;
    }

    public static ServerProxy getServer()
    {
        if(instance == null) {
            server = new ServerProxy();
        }
        return server;
    }

    /**
     * Initialize/reinitialize all of the data in the model from the info returned by the server
     */
    public void initializeData(int versionNumber, Bank bank, List<Message> chat, List<Message> log, Map map,
                               List<Player> playersList, Player player, Trade activeTrade, TurnTracker turnTracker, Player winner) {
        this.versionNumber = versionNumber;
        this.bank = bank;
        this.chat = chat;
        this.log = log;
        this.map = map;
        this.playersList = playersList;
        this.player = player;
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

    public List<Player> getPlayersList(){return playersList; }

    public Player getPlayer(){return player;}


    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPlayersList(List<Player> playersList) {
        this.playersList = playersList;
    }

    public void setUser(Player player) {
        this.player = player;
    }


}
