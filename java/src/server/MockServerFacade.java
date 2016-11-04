package server;

import command.game.GameCreateObjectResult;
import command.game.GameListHolder;
import command.player.AcceptTradeObject;
import command.player.OfferTradeObject;
import model.cards_resources.ResourceCards;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * Created by bvanc on 11/4/2016.
 */
public class MockServerFacade implements IServer {
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
        return false;
    }

    /**
     * Creates a new user account, logs the caller in to server as a new user, and sets their catan.user HTTP cookie
     *
     * @param username player username to register with
     * @param password player password to register with
     * @return success of registration
     * @pre username not null, password not null
     * @post if successful, server returns 200 HTTP success response and new account gets created
     * HTTP response headers set the catan.user cookie
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public boolean userRegister(String username, String password) {
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
        return null;
    }

    /**
     * Creates a new game on the server
     *
     * @param randomTiles
     * @param randomNumbers
     * @param randomPorts
     * @param gameName      String gameName of the new game  @return a JSON object containing properties of the newly created game
     * @pre gameName is not null, randomTiles, randomNumbers, and randomPorts contain valid boolean values
     * @post if successful, server returns 200 HTTP success response, new game gets created with specified properties
     * body containing a JSON object describing the newly created game
     * if invalid, returns a 400 HTTP response with an error message
     */
    @Override
    public GameCreateObjectResult gameCreate(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String gameName) {
        return null;
    }

    /**
     * Adds the player to the specified game and sets their catan.game cookie
     *
     * @param gameID unique identifier for a particular game
     * @param color  player color
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
        return false;
    }

    /**
     * Posts a message to the chat
     *
     * @param playerIndex
     * @param content     the message you want to send  @return success of if the chat contains your message
     * @pre None
     * @post The chat contains your message at the end
     */
    @Override
    public String sendChat(int playerIndex, String content) {
        return null;
    }

    /**
     * Determines if a trade will be accepted or not
     *
     * @param playerIndex
     * @param willAccept  Whether or not you accept the offered trade  @return if the trade was accepted or not
     * @pre You have been offered a trade;
     * you have the required resources
     * @post If accepted, you and the player who offered swap the specified resources;
     * if you decline, no resources are exchanged;
     * the trade offer is removed
     */
    @Override
    public AcceptTradeObject acceptTrade(int playerIndex, boolean willAccept) {
        return null;
    }

    /**
     * Discards the desired cards from the resource hand
     *
     * @param playerIndex
     * @param discardedCards the cards you are discarding  @return success of discarded cards
     * @pre The status of the client model is 'Discarding';
     * you have over 7 cards;
     * you have the cards you're choosing to discard
     * @post You gave up the specified resources;
     * if you're the last one to discard, the client model status changes to 'Robbing'
     */
    @Override
    public String discardCards(int playerIndex, ResourceCards discardedCards) {
        return null;
    }

    /**
     * Rolls the dice
     *
     * @param playerIndex
     * @param number      the number you rolled  @return success of the roll
     * @pre It is your turn;
     * the number is in the range 2-12;
     * the client model's status is 'Rolling'
     * @post The client model's status is now in 'Discarding' or 'Robbing' or 'Playing'
     */
    @Override
    public String rollNumber(int playerIndex, int number) {
        return null;
    }

    /**
     * Builds a road
     *
     * @param playerIndex
     * @param roadLocation the new road's location
     * @param free         whether or not you get this piece for free (i.e., setup)   @return success of built road
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
        return null;
    }

    /**
     * Builds a settlement
     *
     * @param playerIndex
     * @param vertexLocation the location of the settlement
     * @param free           whether or not you get this piece for free(i.e, setup)   @return success of build settlement
     * @pre The settlement location is open;
     * the settlement location is not on water;
     * the settlement location is connected to one of your roads, exept during setup;
     * you have the required resources(1 wood, 1 brick, 1 wheat, 1 sheep = 1 settlement)
     * @post you lost the resources required to build a settlement;
     * the settlement is on the map at the specified location
     */
    @Override
    public String buildSettlement(int playerIndex, VertexLocation vertexLocation, boolean free) {
        return null;
    }

    /**
     * Builds a city
     *
     * @param playerIndex
     * @param vertexLocation the location of the city  @return success of build city
     * @pre The city location is where you currently have a settlement;
     * you have the required resources(2 wheat, 3 ore = 1 city)
     * @post You lost the resources required to build a city;
     * the city is on the map at the specified location;
     * you got a settlement back
     */
    @Override
    public String buildCity(int playerIndex, VertexLocation vertexLocation) {
        return null;
    }

    /**
     * Proposes a trade
     *
     * @param playerIndex
     * @param offer       negative numbers mean you get those cards
     * @param receiver    the recipient of the trade offer(playerIndex)   @return success of offered trade; was it accepted
     * @pre you have the resources you are offering
     * @post the trade is offered to the other player(stored in the server model)
     */
    @Override
    public OfferTradeObject offerTrade(int playerIndex, ResourceCards offer, int receiver) {
        return null;
    }

    /**
     * Trades with a port
     *
     * @param playerIndex
     * @param ratio          trade ratio
     * @param inputResource  what you are giving
     * @param outputResource what you are getting    @return success of a maritime trade
     * @pre You have the resources you are giving;
     * for ratios less than 4, you have the correct port for the trade
     * @post The trade has been executed;
     * the offered resources are in the bank, and the requested resource has been received
     */
    @Override
    public String maritimeTrade(int playerIndex, int ratio, String inputResource, String outputResource) {
        return null;
    }

    /**
     * Robs a player
     *
     * @param playerIndex
     * @param victimIndex -1 if not robbing anyone, else, player you are robbing(playerIndex)
     * @param location    the new robber location   @return success of a robbed player
     * @pre The robber is not being kept in the same location;
     * if a player is being robbed(i.e., victimIndex != 1), the player
     * being robbed has resource cards
     * @post the robber is in the new location;
     * the player is being robbed(if any) gave you one of his resource cards(randomly selected)
     */
    @Override
    public String robPlayer(int playerIndex, int victimIndex, HexLocation location) {
        return null;
    }

    /**
     * @param playerIndex
     * @pre None(except the preconditions for the 'Playing' section)
     * @post The cards in your new dev card hand have been transferred to your old dev card hand;
     * it is the next player's turn
     */
    @Override
    public String finishTurn(int playerIndex) {
        return null;
    }

    /**
     * @param playerIndex
     * @pre You have the required resources(1 ore, 1 wheat, 1 sheep)
     * there are dev cards left in the deck
     * @post You have a new card;
     * if it is a monument card, it has been added to your old dev card hand;
     * if it is a non-monument card, it has been added to your new dev card
     * hand (unplayable this turn)
     */
    @Override
    public String buyDevCard(int playerIndex) {
        return null;
    }

    /**
     * What happens when a soldier dev card is played
     *
     * @param playerIndex
     * @param victimIndex playerIndex, or -1 if you are not robbing anyone
     * @param location    the new robber location   @return success of played soldier dev card
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
        return null;
    }

    /**
     * What happens when a year of plenty dev card is played
     *
     * @param playerIndex
     * @param resource1   the first resource you want to receive
     * @param resource2   the second resource you want to recieve   @return success of a played year of plenty dev card
     * @pre the two specified resources are in the bank
     * @post you gained the two specified resources
     */
    @Override
    public String yearOfPlenty(int playerIndex, ResourceType resource1, ResourceType resource2) {
        return null;
    }

    /**
     * What happens when a road building dev card is played
     *
     * @param playerIndex
     * @param spot1       first road location
     * @param spot2       second road location   @return success of a played road building dev card
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
        return null;
    }

    /**
     * What happens when a monopoly dev card is played
     *
     * @param resource    the resource being taken from the other players
     * @param playerIndex
     * @return success of a played monopoly dev card
     * @pre None(except the general preconditions)
     * @post All of the other players have given you all of their resource cards of the specified type
     */
    @Override
    public String monopoly(String resource, int playerIndex) {
        return null;
    }

    /**
     * What happens when a monument dev card is played
     *
     * @param playerIndex
     * @pre You have enough monument cards to win the game(10 victory points)
     * @post you gained a victory point
     */
    @Override
    public String monument(int playerIndex) {
        return null;
    }
}
