package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.BuildSettlementObject;
import decoder.JsonModels.JsonLocation;
import decoder.JsonModels.JsonPiece;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;
import shared.locations.VertexLocation;

/**
 * Created by
 * jihoon on 11/7/2016.
 */
public class BuildSettlementCommand extends Command {

    private BuildSettlementObject buildSettlementObject;

    public BuildSettlementCommand(HttpExchange httpExchange) {
        super(httpExchange);
        buildSettlementObject = gson.fromJson(json, BuildSettlementObject.class);
    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){
//            String response = ServerFacade.getInstance().buildSettlement(buildSettlementObject.getPlayerIndex(),
//                                                    buildSettlementObject.getVertexLocation(),
//                                                    buildSettlementObject.isFree());

            ServerModel.getInstance().getGame(super.gameId).getMap().addToArray(
                    ServerModel.getInstance().getGame(super.gameId).getMap().getSettlements(),
                    new JsonPiece(null, 0, buildSettlementObject.getVertexLocation().getDir().toString(),
                            new JsonLocation(buildSettlementObject.getVertexLocation().getHexLoc().getX(),
                            buildSettlementObject.getVertexLocation().getHexLoc().getY())));

            ServerModel.getInstance().getGame(super.gameId).incrementVersion();
            String response = ServerModel.getInstance().getGame(super.gameId).getJsonFromModel();

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
