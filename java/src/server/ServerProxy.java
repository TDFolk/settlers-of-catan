package server;

import client.data.GameInfo;
import client.data.PlayerInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import command.game.*;
import command.player.*;
import command.user.LoginObject;
import command.user.RegisterObject;
import model.Game;
import model.cards_resources.ResourceCards;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The ServerProxy acts as the 'in between' for the client and the server. Sends requests to and receives data from
 * the server to give to the client
 * Created by jihoon on 9/16/2016.
 */
public class ServerProxy implements IServer {

    private static ServerProxy server = new ServerProxy();

    public static ServerProxy getServer()
    {
        return server;
    }

    /**
     * @param port port id used to connect to the server
     * @param host host id used to connect to the server
     */
    private String host = "localhost";
    private String port = "8081";
    private String baseUrl = "http://" + host + ":" + port;
    private String catanUsername = null;
    private String catanPlayerID = null;
    private String catanGame = null;
    private String cookie = null;
    private String catanCookie = null;

    public ServerProxy(){

    }

    /**
     *
     * @param port port id used to connect to the server
     * @param host host id used to connect to the server
     */
    public ServerProxy(String host, String port){
        this.host = host;
        this.port = port;
        this.baseUrl = "http://" + host + ":" + port;
    }

    /**
     * Logs the caller in to the server, sets their catan.user HTTP cookie
     *
     * @param username player username to login with
     * @param password player password to login with
     * @return success of login
     * @pre username not null, password not null
     * @post if successful, server returns 200 HTTP success response, HTTP response headers set the catan.user cookie
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public boolean userLogin(String username, String password) {
        String loginCommand = "/user/login";
        LoginObject loginObject = new LoginObject(username, password);
        String postData = loginObject.toJSON();

        try{
            if(doPostCommand(loginCommand, postData) == null){
                return false;
            }
            else{
                catanUsername = username;
                //Game.getInstance().getPlayer().getPlayerInfo().setName(catanUsername);
                return true;
            }

        }
        catch(ConnectException e) {
            e.printStackTrace();

        }
        return false;
    }

    /**
     * Creates a new user account, logs the caller in to server as a new user, and sets their catan.user HTTP cookie
     *
     * @param username player name to register with
     * @param password player password to register with
     * @return success of registration
     * @pre username not null, password not null
     * @post if successful, server returns 200 HTTP success response and new account gets created
     * HTTP response headers set the catan.user cookie
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public boolean userRegister(String username, String password) {
        String registerCommand = "/user/register";
        RegisterObject registerObject = new RegisterObject(username, password);
        String postData = registerObject.toJSON();

        try{
            if(doPostCommand(registerCommand, postData) == null){
                return false;
            }
            else{
                catanUsername = username;
               // Game.getInstance().getPlayer().getPlayerInfo().setName(username);
                return true;
            }
        }
        catch(ConnectException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns information about all the current games on the server
     *
     * @return JSONArray containing a list of objects about the server's games
     * @pre None
     * @post if successful, server returns 200 HTTP success response, and body contains a JSON array with information of games
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public GameListHolder gameList() {
        String gameListCommand = "/games/list";
        Gson gson = new Gson();

        try{
            String result = doGetCommand(gameListCommand);
            if(result == null){
                return null;
            }
            else {
                GameInfo[] gameInfos = gson.fromJson(result, GameInfo[].class);

                //GameListObject[] gameList = gson.fromJson(result, GameListObject[].class);


                for(int i = 0; i < gameInfos.length; i++)
                {
                    ArrayList<PlayerInfo> players = new ArrayList<>();
                    for (PlayerInfo p : gameInfos[i].getPlayers())
                    {
                        if(p.getId() != -1)
                        {
                            players.add(p);
                        }
                    }

                    gameInfos[i].setPlayers(players);

                }


                GameListHolder holder = new GameListHolder();

                holder.setGameInfos(gameInfos);
                holder.setResponse(result);
                return holder;
            }

        }
        catch(ConnectException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a new game on the server
     *
     * @param gameName String gameName of the new game
     * @return a JSON object containing properties of the newly created game
     * @pre gameName is not null, randomTiles, randomNumbers, and randomPorts contain valid boolean values
     * @post if successful, server returns 200 HTTP success response, new game gets created with specified properties
     * body containing a JSON object describing the newly created game
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public GameCreateObjectResult gameCreate(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String gameName) {
        String gameCreateCommand = "/games/create";
        GameCreateObject gameCreateObject = new GameCreateObject(randomTiles, randomNumbers, randomPorts, gameName);
        String postData = gameCreateObject.toJSON();

        try{
            //fetch the response body from the server
            String result = doPostCommand(gameCreateCommand, postData);
            if(result == null){
                return null;
            }
            else{
                //GameCreateObjectResult response = new GameCreateObjectResult();

                //create the object that will hold the response body from server...
                //use gson to create a new object and return it
                Gson gson = new Gson();
                GameCreateObjectResult response = gson.fromJson(result, GameCreateObjectResult.class);
                return response;
            }

        }
        catch(ConnectException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds the player to the specified game and sets their catan.game cookie
     *
     *  String userCookie is the response header
     * @param gameID     unique identifier for a particular game
     * @param color      player color
     * @return success of joining the game
     * @pre valid catan.user HTTP cookie
     * may join game: already in the game OR there is space in the game to add a new player
     * specified game ID is valid
     * specified color is valid
     * @post if successful, server returns 200 HTTP success response, player is in game with specified color
     * server response includes the "Set-cookie" response header settings the catan.game HTTP cookie
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public boolean gameJoin(int gameID, String color) {
        String gameJoinCommand = "/games/join";
        GameJoinObject gameJoinObject = new GameJoinObject(gameID, color);
        String postData = gameJoinObject.toJSON();

        try{
            String result = doPostCommand(gameJoinCommand, postData);
            if(result == null){
                return false;
            }
            else {

                return true;
            }


        }
        catch(ConnectException e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Returns the current state of the game in JSON format and version number
     *
     * @param versionNumber version number of the game
     * @return JSON object of the model
     * @pre valid catan.user and catan.game HTTP cookies
     * version number is included as the "version" query parameter in requested URL, value is valid integer
     * @post if successful, server returns 200 HTTP success response, response body contains JSON data
     * full client model JSON is returned if the caller does not provide a version number
     * "true" is returned if the caller provided a version number and the version number matched the version on server
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public String gameModelVersion(int versionNumber) {
        String gameModelCommand = "/game/model?version=" + versionNumber ;

        try{
            String result = doGetCommand(gameModelCommand);

            return result;

        }
        catch(ConnectException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a list of supported AI player types (LARGEST_ARMY is the only supported type currently)
     *
     * @return list of supported AI player types
     * @pre none
     * @post if successful, server returns 200 HTTP success response
     * body contains a JSON string array enumerating the different types of AI players
     */
    @Override
    public String gameListAI() {
        String gameListAICommand = "/game/listAI";

        try{
            String result = doGetCommand(gameListAICommand);
            return result;

        }
        catch(ConnectException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Adds an AI player to the current game
     *
     * @param AIType type of the AI being added to the game from the listAI
     * @return success of adding an AI
     * @pre valid catan.user and catan.game HTTP cookies, space in the game for another player, "AIType" is valid
     * @post if successful, server returns 200 HTTP success response, new AI player of type has been added to the game
     * server selected a name and color for the player
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public boolean gameAddAI(String AIType) {
        String gameAddAICommand = "/game/addAI";
        GameAddAIObject gameAddAIObject = new GameAddAIObject(AIType);

        String postData = gameAddAIObject.toJSON();

        try{
            doPostCommand(gameAddAICommand, postData);
            return true;
        }
        catch(ConnectException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Posts a message to the chat
     *
     * @param content the message you want to send
     * @return success of if the chat contains your message
     * @pre None
     * @post The chat contains your message at the end
     */
    @Override
    public String sendChat(int playerIndex, String content) {
        String sendChatCommand = "/moves/sendChat";
        SendChatObject sendChatObject = new SendChatObject(playerIndex, content);
        String postData = sendChatObject.toJSON();

        try{
            return doPostCommand(sendChatCommand, postData);

        }
        catch(ConnectException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Determines if a trade will be accepted or not
     *
     * @param willAccept Whether or not you accept the offered trade
     * @return if the trade was accepted or not
     * @pre You have been offered a trade;
     * you have the required resources
     * @post If accepted, you and the player who offered swap the specified resources;
     * if you decline, no resources are exchanged;
     * the trade offer is removed
     */
    @Override
    public String acceptTrade(int playerIndex, boolean willAccept) {
        String acceptTradeCommand = "/moves/acceptTrade";
        AcceptTradeObject acceptTradeObject = new AcceptTradeObject(playerIndex, willAccept);
        String postData = acceptTradeObject.toJSON();

        try{
            return doPostCommand(acceptTradeCommand, postData);
        }
        catch(ConnectException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Discards the desired cards from the resource hand
     *
     * @param discardedCards the cards you are discarding
     * @return success of discarded cards
     * @pre The status of the client model is 'Discarding';
     * you have over 7 cards;
     * you have the cards you're choosing to discard
     * @post You gave up the specified resources;
     * if you're the last one to discard, the client model status changes to 'Robbing'
     */
    @Override
    public String discardCards(int playerIndex, ResourceCards discardedCards) {
        String discardCardsCommand = "/moves/discardCards";
        DiscardCardsObject discardCardsObject = new DiscardCardsObject(playerIndex, discardedCards);
        String postData = discardCardsObject.toJSON();

        try{
            String result = doPostCommand(discardCardsCommand, postData);

            return result;
        }
        catch(ConnectException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Rolls the dice
     *
     * @param number the number you rolled
     * @return success of the roll
     * @pre It is your turn;
     * the number is in the range 2-12;
     * the client model's status is 'Rolling'
     * @post The client model's status is now in 'Discarding' or 'Robbing' or 'Playing'
     */
    @Override
    public String rollNumber(int playerIndex, int number) {
        String rollNumberCommand = "/moves/rollNumber";
        RollNumberObject rollNumberObject = new RollNumberObject(playerIndex, number);
        String postData = rollNumberObject.toJSON();

        try{
            String result = doPostCommand(rollNumberCommand, postData);
            return result;
        }
        catch(ConnectException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Builds a road
     *
     * @param free       whether or not you get this piece for free (i.e., setup)
     * @param roadLocation the new road's location
     * @return success of built road
     * @pre The road location is open;
     * the road location is connected to another road owned by the player;
     * the road location is not on water;
     * you have the required resources(1 wood, 1 brick = 1 road);
     * Setup round: Must be placed by settlement owned by the player with no adjacent road
     * @post You lost the resources required to build a road;
     * the road is on the map at the specified location;
     * if applicable, "longest road" is awarded
     */
    @Override
    public String buildRoad(int playerIndex, EdgeLocation roadLocation, boolean free) {
        String buildRoadCommand = "/moves/buildRoad";
        BuildRoadObject buildRoadObject = new BuildRoadObject(playerIndex, roadLocation, free);
        String postData = buildRoadObject.toJSON();

        try{
            return doPostCommand(buildRoadCommand, postData);
        }
        catch(ConnectException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Builds a settlement
     *
     * @param free         whether or not you get this piece for free(i.e, setup)
     * @param vertexLocation the location of the settlement
     * @return success of build settlement
     * @pre The settlement location is open;
     * the settlement location is not on water;
     * the settlement location is connected to one of your roads, exept during setup;
     * you have the required resources(1 wood, 1 brick, 1 wheat, 1 sheep = 1 settlement)
     * @post you lost the resources required to build a settlement;
     * the settlement is on the map at the specified location
     */
    @Override
    public String buildSettlement(int playerIndex, VertexLocation vertexLocation, boolean free) {
        String buildSettlementCommand = "/moves/buildSettlement";
        BuildSettlementObject buildSettlementObject = new BuildSettlementObject(playerIndex, vertexLocation, free);
        String postData = buildSettlementObject.toJSON();
        try{
            return doPostCommand(buildSettlementCommand, postData);
        }
        catch(ConnectException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Builds a city
     *
     * @param vertexLocation the location of the city
     * @return success of build city
     * @pre The city location is where you currently have a settlement;
     * you have the required resources(2 wheat, 3 ore = 1 city)
     * @post You lost the resources required to build a city;
     * the city is on the map at the specified location;
     * you got a settlement back
     */
    @Override
    public String buildCity(int playerIndex, VertexLocation vertexLocation) {
        String buildCityCommand = "/moves/buildCity";
        BuildCityObject buildCityObject = new BuildCityObject(playerIndex, vertexLocation);
        String postData = buildCityObject.toJSON();

        try{
            return doPostCommand(buildCityCommand, postData);
        }
        catch(ConnectException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Proposes a trade
     *
     * @param offer    negative numbers mean you get those cards
     * @param receiver the recipient of the trade offer(playerIndex)
     * @return
     * @pre you have the resources you are offering
     * @post the trade is offered to the other player(stored in the server model)
     */
    @Override
    public String offerTrade(int playerIndex, ResourceCards offer, int receiver) {
        String offerTradeCommand = "/moves/offerTrade";
        OfferTradeObject offerTradeObject = new OfferTradeObject(playerIndex, offer, receiver);
        String postData = offerTradeObject.toJSON();

        try{

            return doPostCommand(offerTradeCommand, postData);
        }
        catch(ConnectException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Trades with a port
     *
     * @param ratio          trade ratio
     * @param inputResource  what you are giving
     * @param outputResource what you are getting
     * @return success of a maritime trade
     * @pre You have the resources you are giving;
     * for ratios less than 4, you have the correct port for the trade
     * @post The trade has been executed;
     * the offered resources are in the bank, and the requested resource has been received
     */
    @Override
    public String maritimeTrade(int playerIndex, int ratio, String inputResource, String outputResource) {
        String maritimeTradeCommand = "/moves/maritimeTrade";
        MaritimeTradeObject maritimeTradeObject = new MaritimeTradeObject(playerIndex, ratio, inputResource, outputResource);
        String postData = maritimeTradeObject.toJSON();

        try{
            return doPostCommand(maritimeTradeCommand, postData);

        }
        catch(ConnectException e){
            e.printStackTrace();
        }


        return null;
    }

    /**
     * Robs a player
     *
     * @param location    the new robber location
     * @param victimIndex -1 if not robbing anyone, else, player you are robbing(playerIndex)
     * @return success of a robbed player
     * @pre The robber is not being kept in the same location;
     * if a player is being robbed(i.e., victimIndex != 1), the player
     * being robbed has resource cards
     * @post the robber is in the new location;
     * the player is being robbed(if any) gave you one of his resource cards(randomly selected)
     */
    @Override
    public String robPlayer(int playerIndex, int victimIndex, HexLocation location) {
        String robPlayerCommand = "/moves/robPlayer";
        RobPlayerObject robPlayerObject = new RobPlayerObject(playerIndex, victimIndex, location);
        String postData = robPlayerObject.toJSON();

        try{
            return doPostCommand(robPlayerCommand, postData);

        }
        catch(ConnectException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @pre None(except the preconditions for the 'Playing' section)
     * @post The cards in your new dev card hand have been transferred to your old dev card hand;
     * it is the next player's turn
     */
    @Override
    public String finishTurn(int playerIndex) {
        String finishTurnCommand = "/moves/finishTurn";
        FinishTurnObject finishTurnObject = new FinishTurnObject(playerIndex);
        String postData = finishTurnObject.toJSON();
        try{
            return doPostCommand(finishTurnCommand, postData);
        }
        catch(ConnectException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @pre You have the required resources(1 ore, 1 wheat, 1 sheep)
     * there are dev cards left in the deck
     * @post You have a new card;
     * if it is a monument card, it has been added to your old dev card hand;
     * if it is a non-monument card, it has been added to your new dev card
     * hand (unplayable this turn)
     */
    @Override
    public String buyDevCard(int playerIndex) {
        String buyDevCardCommand = "/moves/buyDevCard";
        BuyDevCardObject buyDevCardObject = new BuyDevCardObject(playerIndex);
        String postData = buyDevCardObject.toJSON();
        try{
            return doPostCommand(buyDevCardCommand, postData);
        }
        catch(ConnectException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * What happens when a soldier dev card is played
     *
     * @param location    the new robber location
     * @param victimIndex playerIndex, or -1 if you are not robbing anyone
     * @return success of played soldier dev card
     * @pre The robber is not being kept in the same location;
     * if a player is being robbed(i.e., victimIndex != -1), the player
     * being robbed has resource cards
     * @post The robber is in the new location;
     * the player being robbed(if any) gave you one of his resource cards (randomly selected);
     * if applicable, "largest army" is awarded;
     * you are not allowed to play other dev cards during this turn(except monument)
     */
    @Override
    public String soldier(int playerIndex, int victimIndex, HexLocation location) {
        String soldierCommand = "/moves/Soldier";
        SoldierObject soldierObject = new SoldierObject(playerIndex, victimIndex, location);
        String postData = soldierObject.toJSON();

        try{
            return doPostCommand(soldierCommand, postData);
        }
        catch(ConnectException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * What happens when a year of plenty dev card is played
     *
     * @param resource1 the first resource you want to receive
     * @param resource2 the second resource you want to recieve
     * @return success of a played year of plenty dev card
     * @pre the two specified resources are in the bank
     * @post you gained the two specified resources
     */
    @Override
    public String yearOfPlenty(int playerIndex, ResourceType resource1, ResourceType resource2) {
        String yearOfPlentyCommand = "/moves/Year_of_Plenty";
        YearOfPlentyObject yearOfPlentyObject = new YearOfPlentyObject(playerIndex, resource1, resource2);
        String postData = yearOfPlentyObject.toJSON();
        try{
            return doPostCommand(yearOfPlentyCommand, postData);
        }
        catch(ConnectException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * What happens when a road building dev card is played
     *
     * @param spot1 first road location
     * @param spot2 second road location
     * @return success of a played road building dev card
     * @pre The first road location(spot1) is connected to one of your roads;
     * the second road location(spot2) is connected to one of your roads or to
     * the first road location(spot1);
     * neither road location is on water;
     * you have at least two unused roads
     * @post You have two fewer unused roads;
     * two new roads appear on the map at the specified locations;
     * if applicable, "longest road" has been awarded
     */
    @Override
    public String roadBuilding(int playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
        String roadBuildingCommand = "/moves/Road_Building";
        RoadBuildingObject roadBuildingObject = new RoadBuildingObject(playerIndex, spot1, spot2);
        String postData = roadBuildingObject.toJSON();

        try{
            return doPostCommand(roadBuildingCommand, postData);
        }

        catch(ConnectException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * What happens when a monopoly dev card is played
     *
     * @param resource the resource being taken from the other players
     * @return success of a played monopoly dev card
     * @pre None(except the general preconditions)
     * @post All of the other players have given you all of their resource cards of the specified type
     */
    @Override
    public String monopoly(String resource, int playerIndex) {
        String monopolyCommand = "/moves/Monopoly";
        MonopolyObject monopolyObject = new MonopolyObject(resource, playerIndex);
        String postData = monopolyObject.toJSON();

        try{
            return doPostCommand(monopolyCommand, postData);
        }
        catch(ConnectException e){
            e.printStackTrace();
        }


        return null;
    }

    /**
     * What happens when a monument dev card is played
     *
     * @pre You have enough monument cards to win the game(10 victory points)
     * @post you gained a victory point
     */
    @Override
    public String monument(int playerIndex) {
        String monumentCommand = "/moves/Monument";
        MonumentObject monumentObject = new MonumentObject(playerIndex);
        String postData = monumentObject.toJSON();

        try{
            return doPostCommand(monumentCommand, postData);
        }
        catch(ConnectException e){
            e.printStackTrace();
        }
        return null;
    }

    private String doPostCommand (String methodName, Object output) throws ConnectException{
        URL url;
        try {
            String urlCommand = baseUrl + methodName;
            url = new URL(urlCommand);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true); //set true in order to send server our response body

            if(catanCookie != null) {
                connection.setRequestProperty("Cookie", catanCookie + "; catan.game=" + catanGame);
            }

            connection.connect();
            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
            osw.write(output.toString());
            osw.flush();

            //if the connection returns a 200, do this
            if(connection.getResponseCode() == connection.HTTP_OK){
                String header;
                int i = 1;
                while((header = connection.getHeaderFieldKey(i)) != null){
                    if(header.equals("Set-cookie")){
                        //get the cookie
                        String foo = connection.getHeaderField(i);
                        //catanGame = foo.substring(11, cookie.indexOf(';'));
                        //trim the cookie where the ';' starts and initialize the cookie variable
                        cookie = foo.split(";", 2)[0];
                        if(catanCookie == null){
                            catanCookie = foo.split(";", 2)[0];
                        }
                        if(catanUsername == null){
                            String fooPlayerID = cookie.split("playerID%")[1];
                            catanPlayerID = fooPlayerID.split("3A", 2)[1];
                            catanPlayerID = catanPlayerID.split("%")[0];
                        }
                        if(catanGame == null && foo.contains("catan.game=")){
                            String bar = foo.split("=", 2)[1];
                            catanGame = bar.split(";", 2)[0];
                        }


                    }
                    i++;
                }
                InputStream input = connection.getInputStream();
                return getResponseBodyData(input);
            }
            else {
                throw new ConnectException(String.format("doPostCommand failed... %s (http code %d)", methodName,
                        connection.getResponseCode()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String doGetCommand (String methodName) throws ConnectException{
        URL url;
        try {

            //Concatenate the entire URL command
            String urlCommand = baseUrl + methodName;
            url = new URL(urlCommand);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");


            if(catanCookie != null) {
                connection.setRequestProperty("Cookie", catanCookie + "; catan.game=" + catanGame);

                //connection.connect();
            }

            //Checks to see if the server returns a 200 response
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream input = connection.getInputStream();
                return getResponseBodyData(input);
            }

//            if it's a bad response, the server returns a 400 response
//            else if(connection.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
//
//            }
//            throw a new exception if server returns a 400 response

            else {
                throw new ConnectException(String.format("doGetCommand failed... %s (http code %d)", methodName,
                        connection.getResponseCode()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getResponseBodyData(InputStream input) throws IOException{
        byte[] buffer = new byte[1024];
        int length = 0;
        StringBuilder sb = new StringBuilder();
        while((length = input.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, length));
        }
        return sb.toString();
    }

    public static void setServer(ServerProxy server) {
        ServerProxy.server = server;
    }

    public String getCatanUsername() {
        return catanUsername;
    }

    public String getCatanGame() {
        return catanGame;
    }

    public String getCookie() {
        return cookie;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getCatanPlayerID() {
        return catanPlayerID;
    }

}
