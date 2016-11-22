package server.serverModel;

import command.game.GameCreateObject;
import command.game.GameCreateObjectResult;
import command.game.GameListHolder;
import command.game.GameListObject;
import command.player.PlayerObject;
import decoder.JsonModels.JsonPlayer;
import shared.definitions.CatanColor;

import java.util.*;
import java.util.logging.Logger;

/**
 * Model for all games running on the server as well as all of the registered users
 *
 * Created by bvanc on 11/7/2016.
 */
public class ServerModel {

    private Logger logger = null;

    private static ServerModel instance = null;

    private ServerModel() {

        games = new TreeMap<>();

        String defaultTitle = "DEFAULT GAME, YO!";
        String defaultTitle2 = "DEFAULT GAME, DAWG!";

        ServerGameModel defaultGame = new ServerGameModel(100);
        ServerGameModel defaultGame2 = new ServerGameModel(101);

        defaultGame.setPlayers(new JsonPlayer[4]);
        defaultGame2.setPlayers(new JsonPlayer[4]);

        games.put(defaultTitle, defaultGame);
        games.put(defaultTitle2, defaultGame2);




    }

    public static ServerModel getInstance() {

        if(instance == null)
        {
            instance = new ServerModel();
        }

        return instance;
    }
    private static int game_ID = 0;
    private Map<String, ServerGameModel> games;
    private Map<String, String> usersNAME_PASS = new TreeMap<>();
    private ArrayList<String> registeredUsers = new ArrayList<>();
    private List<String> loggedInUsers = null;

    /**
     * Logs a user into the server if they are already registered
     * @return success of login
     */
    public boolean userLogin(String userName, String passWord)
    {
        if(loggedInUsers == null)
        {
            loggedInUsers = new ArrayList<>();
        }

        //check if user registered
        if(alreadyRegistered(userName))
        {
            //is the password the same?
            if(usersNAME_PASS.get(userName).equals(passWord))
            {
                loggedInUsers.add(userName);
                return true;
            }
            else
            {
                //wrong password
                return false;

            }

        }

        return false;

    }

    /**Registers a user if they are not already registered
     *
     * @param userName
     * @param password
     * @return success of register
     */
    public boolean userRegister(String userName, String password)
    {
        //check if not registered
        if(!alreadyRegistered(userName))
        {
            //if not registered, add to list
            usersNAME_PASS.put(userName, password);
            registeredUsers.add(userName);
            return true;
        }

        return false;
    }

    public int getUserID(String userName)
    {
        for(int i = 0; i < registeredUsers.size(); i++)
        {
            if(registeredUsers.get(i).equals(userName))
            {
                return i;
            }
        }

        return -1;

    }

    public GameCreateObjectResult gameCreate(boolean randomTiles,
                                             boolean randomNumbers,
                                             boolean randomPorts,
                                             String gameName) {

        GameCreateObjectResult result = new GameCreateObjectResult();
        result.setId(game_ID);
        result.setTitle(gameName);
        result.setPlayers(new PlayerObject[4]);

        ServerGameModel newGame = new ServerGameModel(game_ID);
        game_ID++;
        games.put(gameName, newGame);

        return result;

    }

    /**
     * Lists the possible AI to add to each game
     * @return json string of each AI
     */
    public String listAI(){return listAIJson();}

    /**
     * Creates a new game on the server
     * @return success of creation
     */
    public boolean createGame(){

        
        return false;

    }


    private String listGamesJSON() {

        return "list of games";
    }

    private String listAIJson() {

        return "list of AI";

    }


    private boolean alreadyRegistered(String name)
    {

        if(usersNAME_PASS.containsKey(name))
        {
            return true;
        }

        return false;

    }

    public String gameModel(int gameID, int versionNumber)
    {
        for(Map.Entry<String, ServerGameModel> game : games.entrySet())
        {
            if(game.getValue().getGameID() == gameID)
            {
                if(game.getValue().getVersion() != versionNumber)
                {
                    return game.getValue().gameModel();
                }
                else
                {
                    return "true";
                }
            }
        }

        String errorMessage = "ERROR. ServerModel.gameModel() No model with ID " + gameID + " found.";
        System.out.println(errorMessage);
        return errorMessage;
    }


    /* Json Array
                [{"title":"Default Game",
                "id":0,
                "players":[{"color":"orange","name":"Sam","id":0},
                    {"color":"blue","name":"Brooke","id":1},
                    {"color":"red","name":"Pete","id":10},
                    {"color":"green","name":"Mark","id":11}]},

                 {"title":"AI Game",
                 "id":1,
                 "players":[{"color":"orange","name":"Pete","id":10},
                    {"color":"white","name":"Squall","id":-2},
                    {"color":"purple","name":"Miguel","id":-3},
                    {"color":"puce","name":"Ken","id":-4}]},

                 {"title":"Empty Game",
                 "id":2,
                 "players":[{"color":"orange","name":"Sam","id":0},
                    {"color":"blue","name":"Brooke","id":1},
                    {"color":"red","name":"Pete","id":10},
                    {"color":"green","name":"Mark","id":11}]},
                 {"title":"RoblocksSSSS",
                 "id":3,
                 "players":[{"color":"blue","name":"aaa","id":12}
                            ,{},{},{}]}]
                */

    public GameListHolder gameList() {

        //Object that holds the list of gameListObjects
        GameListHolder gameListHold = new GameListHolder();

        ArrayList<GameListObject> gameListObjects = new ArrayList<>();

        for(Map.Entry<String, ServerGameModel> game : games.entrySet()) {


            GameListObject gameList = new GameListObject();

            String currentGameTitle = game.getKey();
            ServerGameModel currentGame = game.getValue();

            int id = currentGame.getGameID();

            JsonPlayer[] players = currentGame.getPlayers();

            gameList.setId(id);
            gameList.setTitle(currentGameTitle);

            PlayerObject[] playerList = new PlayerObject[4];

            for (int i = 0; i < players.length; i++) {

                PlayerObject player = null;

                if(players[i] != null) {
                    CatanColor color = null;
                    switch (players[i].getColor()) {
                        //RED, ORANGE, YELLOW, BLUE, GREEN, PURPLE, PUCE, WHITE, BROWN;

                        case "red":
                            color = CatanColor.RED;
                            break;
                        case "orange":
                            color = CatanColor.ORANGE;
                            break;
                        case "yellow":
                            color = CatanColor.YELLOW;
                            break;
                        case "blue":
                            color = CatanColor.BLUE;
                            break;
                        case "green":
                            color = CatanColor.GREEN;
                            break;
                        case "purple":
                            color = CatanColor.PURPLE;
                            break;
                        case "puce":
                            color = CatanColor.PUCE;
                            break;
                        case "white":
                            color = CatanColor.WHITE;
                            break;
                        case "brown":
                            color = CatanColor.BROWN;
                            break;
                        default:
                            color = CatanColor.BLUE;

                    }

                    player = new PlayerObject(color, players[i].getName(), players[i].getPlayerID());

                    playerList[i] = player;

                }

                gameList.setPlayers(playerList);
            }

            gameListObjects.add(gameList);

        }

        gameListHold.setGameListObjects(gameListObjects.toArray(new GameListObject[0]));

        return gameListHold;

    }
}
