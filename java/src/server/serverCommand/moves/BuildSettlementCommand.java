package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.BuildSettlementObject;
import decoder.JsonModels.JsonLocation;
import decoder.JsonModels.JsonPiece;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 * Created by
 * jihoon on 11/7/2016.
 */
public class BuildSettlementCommand extends Command {

    private BuildSettlementObject buildSettlementObject;
    private VertexLocation vertexLocation;
    private VertexDirection vertexDirection;
    private int x;
    private int y;

    public BuildSettlementCommand(HttpExchange httpExchange) {
        super(httpExchange);

        json = json.replaceAll("\\n", "");
//        String split = json.split("\"");


        buildSettlementObject = gson.fromJson(json, BuildSettlementObject.class);


        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        vertexLocation = fetchVertexLocation(jsonObject.get("vertexLocation").getAsJsonObject());

        vertexDirection = vertexLocation.getDir();
        x = vertexLocation.getHexLoc().getX();
        y = vertexLocation.getHexLoc().getY();

    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){
            if(buildSettlementObject.isFree()){
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[buildSettlementObject.getPlayerIndex()].decrementSettlement();
            }
            else {
                ServerModel.getInstance().getGame(super.gameId).getPlayers()[buildSettlementObject.getPlayerIndex()].buySettlement();
            }

            ServerModel.getInstance().getGame(super.gameId).getMap().setSettlements(ServerModel.getInstance().getGame(super.gameId).getMap().addToArray(
                    ServerModel.getInstance().getGame(super.gameId).getMap().getSettlements(),
                    new JsonPiece(null, 0, vertexDirection.toString(), new JsonLocation(x, y, vertexDirection.toString())),
                    buildSettlementObject.getPlayerIndex()));

            ServerModel.getInstance().getGame(super.gameId).incrementVersion();
            String response = ServerModel.getInstance().getGame(super.gameId).getJsonFromModel();


            //increment points here????
            ServerModel.getInstance().getGame(super.gameId).getPlayers()[buildSettlementObject.getPlayerIndex()].incrementPoints();


            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {
                // Returns the client model JSON (identical to /game/model)
                return new JsonPrimitive(response);
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }
}
