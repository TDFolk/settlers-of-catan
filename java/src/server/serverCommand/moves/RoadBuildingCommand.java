package server.serverCommand.moves;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;

import command.player.RoadBuildingObject;
import decoder.JsonModels.JsonLocation;
import decoder.JsonModels.JsonPiece;
import server.ServerFacade;
import server.serverCommand.Command;
import server.serverModel.ServerGameModel;
import server.serverModel.ServerModel;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

/**
 * Created by jihoon on 11/7/2016.
 */
public class RoadBuildingCommand extends Command {
	
//	private int playerIndex;
	private EdgeLocation spot1;
	private EdgeLocation spot2;
    private RoadBuildingObject roadBuildingObject;


    public RoadBuildingCommand(HttpExchange httpExchange) {
        super(httpExchange);
        json = json.replaceAll("\\n", "");
        roadBuildingObject = gson.fromJson(json, RoadBuildingObject.class);

        //TODO: CHECK THIS SYNTAX
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        JsonObject spot1Object = jsonObject.get("spot1").getAsJsonObject();

        int x = spot1Object.get("x").getAsInt();
        int y = spot1Object.get("y").getAsInt();
        String dir = spot1Object.get("direction").getAsString();
        dir = whatDirection(dir);
        spot1 = new EdgeLocation(new HexLocation(x, y), EdgeDirection.valueOf(dir));

        JsonObject spot2Object = jsonObject.get("spot2").getAsJsonObject();
        int x2 = spot2Object.get("x").getAsInt();
        int y2 = spot2Object.get("y").getAsInt();
        String dir2 = spot2Object.get("direction").getAsString();
        dir2 = whatDirection(dir2);
        spot2 = new EdgeLocation(new HexLocation(x2, y2), EdgeDirection.valueOf(dir2));

    }

    /**
     * This method will handle executing the commands
     *
     * @return returns a json element from the given jsonString
     */
    @Override
    public JsonElement execute() {
        if(super.hasGameCookie && super.hasUserCookie){

            //decrement road total twice
            ServerModel.getInstance().getGame(super.gameId).getPlayers()[roadBuildingObject.getPlayerIndex()].decrementRoadTotal();
            ServerModel.getInstance().getGame(super.gameId).getPlayers()[roadBuildingObject.getPlayerIndex()].decrementRoadTotal();

//            ServerModel.getInstance().getGame(super.gameId).getMap().setRoads()

            ServerModel.getInstance().getGame(super.gameId).getMap().setRoads(ServerModel.getInstance().getGame(super.gameId).getMap().addToArray(
                    ServerModel.getInstance().getGame(super.gameId).getMap().getRoads(),
                    new JsonPiece(null, 0, spot1.getDir().toString(), new JsonLocation(spot1.getHexLoc().getX(), spot1.getHexLoc().getY(), spot1.getDir().toString())),
                    roadBuildingObject.getPlayerIndex()));


            ServerModel.getInstance().getGame(super.gameId).getMap().setRoads(ServerModel.getInstance().getGame(super.gameId).getMap().addToArray(
                    ServerModel.getInstance().getGame(super.gameId).getMap().getRoads(),
                    new JsonPiece(null, 0, spot1.getDir().toString(), new JsonLocation(spot2.getHexLoc().getX(), spot2.getHexLoc().getY(), spot2.getDir().toString())),
                    roadBuildingObject.getPlayerIndex()));


            String userName = ServerModel.getInstance().getUsernameFromID(super.playerId);
            String historyMessage = userName + " has built a road";
            ServerModel.getInstance().getGame(super.gameId).addLog(historyMessage, userName);
            ServerModel.getInstance().getGame(super.gameId).addLog(historyMessage, userName);
            ServerModel.getInstance().getGame(super.gameId).incrementVersion();
            String response = ServerModel.getInstance().getGame(super.gameId).getJsonFromModel();

            if(response == null){
                return new JsonPrimitive("Invalid");
            }
            else {


                return new JsonPrimitive(response);
            }
        }
        else {
            return new JsonPrimitive("catan.game and/or catan.user cookies are missing");
        }
    }
}
