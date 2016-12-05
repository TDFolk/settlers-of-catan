package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.BuildSettlementObject;
import decoder.JsonModels.JsonLocation;
import decoder.JsonModels.JsonPiece;
import model.Game;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;
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
            ServerGameModel game = ServerModel.getInstance().getGame(super.gameId);
            HexLocation hexLocation = vertexLocation.getNormalizedLocation().getHexLoc();
            VertexDirection vertexDirection = vertexLocation.getNormalizedLocation().getDir();

            if(buildSettlementObject.isFree()){
                game.getPlayers()[buildSettlementObject.getPlayerIndex()].decrementSettlement();
            }
            else {
                game.getPlayers()[buildSettlementObject.getPlayerIndex()].buySettlement();
            }

            game.getMap().setSettlements(game.getMap().addToArray(
                    game.getMap().getSettlements(),
                    new JsonPiece(vertexDirection.toString(), new JsonLocation(x, y, vertexDirection.toString()), hexLocation, vertexDirection),
                    buildSettlementObject.getPlayerIndex()));

            if (game.getTurnTracker().getStatus().equals("SecondRound")) {
                game.grabProperResources(hexLocation, buildSettlementObject.getPlayerIndex(), vertexDirection, false);
            }

            String response = ServerModel.getInstance().getGame(super.gameId).getJsonFromModel();


            //increment points here????
            ServerModel.getInstance().getGame(super.gameId).getPlayers()[buildSettlementObject.getPlayerIndex()].incrementPoints();


            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {
                String userName = ServerModel.getInstance().getUsernameFromID(super.playerId);
                String historyMessage = userName + " has built a settlement";
                ServerModel.getInstance().getGame(super.gameId).addLog(historyMessage, userName);

                // Returns the client model JSON (identical to /game/model)
                ServerModel.getInstance().getGame(super.gameId).incrementVersion();
                return new JsonPrimitive(response);
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }
}
