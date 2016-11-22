package server.serverModel;

import command.game.GameCreateObjectResult;
import command.game.GameListHolder;

/**
 * Created by bvanc on 11/7/2016.
 */
public class ServerModelFacade {

    private static ServerModelFacade instance = null;

    private ServerModelFacade() {
    }

    public static ServerModelFacade getInstance() {

        if(instance == null)
        {
            instance = new ServerModelFacade();
        }

        return instance;
    }

    public boolean userLogin(String userName, String password)
    {
        return ServerModel.getInstance().userLogin(userName, password);
    }

    public boolean userRegister(String userName, String password)
    {
        return ServerModel.getInstance().userRegister(userName, password);
    }

    public GameCreateObjectResult gameCreate(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String gameName)
    {
        return ServerModel.getInstance().gameCreate(randomTiles, randomNumbers, randomPorts, gameName);
    }

    /**changes the data of the model according to the trade accepted
     *
     * @return the entire model of the game in Json string form
     */
    public String acceptTrade(){return "model";}

    /**changes the data of the model according to the city being built
     *
     * @return the entire model of the game in Json string form
     */
    public String buildCity(){return "model";}

    /**changes the data of the model according to the road being built
     *
     * @return the entire model of the game in Json string form
     */
    public String buildRoad(){return "model";}

    /**changes the data of the model according to the settlement being built
     *
     * @return the entire model of the game in Json string form
     */
    public String buildSettlement(){return "model";}

    /**changes the data of the model according to the  dev card being built
     *
     * @return the entire model of the game in Json string form
     */
    public String buyDevCard(){return "model";}

    /**changes the data of the model according to the discard cards
     *
     * @return the entire model of the game in Json string form
     */
    public String discardCards(){return "model";}

    /**changes the data of the model according to the turn being finished
     *
     * @return the entire model of the game in Json string form
     */
    public String finishTurn(){return "model";}

    /**changes the data of the model according to the maritime trade being offered
     *
     * @return the entire model of the game in Json string form
     */
    public String maritimeTrade(){return "model";}

    /**changes the data of the model according to the monopoly card being played
     *
     * @return the entire model of the game in Json string form
     */
    public String monopoly(){return "model";}

    /**changes the data of the model according to the monument card being played
     *
     * @return the entire model of the game in Json string form
     */
    public String monument(){return "model";}

    /**changes the data of the model according to the trade being offered
     *
     * @return the entire model of the game in Json string form
     */
    public String offerTrade(){return "model";}

    /**changes the data of the model according to the road building card being played
     *
     * @return the entire model of the game in Json string form
     */
    public String roadBuilding(){return "model";}

    /**changes the data of the model according to the rob taking place
     *
     * @return the entire model of the game in Json string form
     */
    public String rob(){return "model";}

    /**changes the data of the model according to the roll being rolled
     *
     * @return the entire model of the game in Json string form
     */
    public String roll(){return "model";}

    /**changes the data of the model according to the chat being sent
     *
     * @return the entire model of the game in Json string form
     */
    public String sendChat(){return "model";}

    /**changes the data of the model according to the soldier card being played
     *
     * @return the entire model of the game in Json string form
     */
    public String soldier(){return "model";}

    /**changes the data of the model according to the year of plenty being played
     *
     * @return the entire model of the game in Json string form
     */
    public String yearOfPlenty(){return "model";}

    public String gameModel(int versionNumber, int gameID)
    {

        return ServerModel.getInstance().gameModel(versionNumber, gameID);

    }

    public GameListHolder gameList() {

        return ServerModel.getInstance().gameList();

    }

    public int getUserID(String userName) {

        return ServerModel.getInstance().getUserID(userName);

    }

    public boolean joinGame(int gameID, String color, int playerID) {

        return ServerModel.getInstance().joinGame(gameID, color, playerID);
    }
}
