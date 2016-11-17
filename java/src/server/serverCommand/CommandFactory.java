package server.serverCommand;

import com.sun.net.httpserver.HttpExchange;
import model.cards_resources.ResourceCards;
import server.serverCommand.game.AddAICommand;
import server.serverCommand.game.ListAICommand;
import server.serverCommand.game.ModelCommand;
import server.serverCommand.games.CreateCommand;
import server.serverCommand.games.JoinCommand;
import server.serverCommand.games.ListCommand;
import server.serverCommand.moves.*;
import server.serverCommand.user.LoginCommand;
import server.serverCommand.user.RegisterCommand;
import shared.definitions.ResourceType;
import shared.locations.*;


/**
 * Created by Tanner on 11/16/2016.
 */
public class CommandFactory {
    private String uri;

    public Command create(HttpExchange exchange) {
        uri = exchange.getRequestURI().toString();
        String[] uri_request = uri.split("/");
        String command_request = uri_request[uri_request.length - 1];

        switch (command_request) {
            case "login":
                return new LoginCommand(exchange, "", "");
            case "register":
                return new RegisterCommand(exchange, "", "");
            case "list":
                return new ListCommand(exchange);
            case "create":
                return new CreateCommand(exchange, false, false, false, "");
            case "join":
                return new JoinCommand(exchange, 0, "");
            case "model":
                return new ModelCommand(exchange, 0);
            case "addAI":
                return new AddAICommand(exchange, "");
            case "listAI":
                return new ListAICommand(exchange);
            case "sendChat":
                return new SendChatCommand(exchange, 0, "");
            case "rollNumber":
                return new RollCommand(exchange, 0, 0);
            case "robPlayer":
                return new RobCommand(exchange, 0, 0, new HexLocation(0, 0));
            case "finishTurn":
                return new FinishTurnCommand(exchange, 0);
            case "buyDevCard":
                return new BuyDevCardCommand(exchange, 0);
            case "Year_of_Plenty":
                return new YearOfPlentyCommand(exchange, 0, ResourceType.BRICK, ResourceType.BRICK);
            case "Road_Building":
                return new RoadBuildingCommand(exchange, 0, new EdgeLocation(new HexLocation(0, 0),
                        EdgeDirection.South), new EdgeLocation(new HexLocation(0, 0),
                        EdgeDirection.South));
            case "Soldier":
                return new SoldierCommand(exchange, 0, 0, new HexLocation(0, 0));
            case "Monopoly":
                return new MonopolyCommand(exchange, "", 0);
            case "Monument":
                return new MonumentCommand(exchange, 0);
            case "buildRoad":
                return new BuildRoadCommand(exchange, 0, new EdgeLocation(new HexLocation(0, 0),
                        EdgeDirection.South), false);
            case "buildSettlement":
                return new BuildSettlementCommand(exchange, 0, new VertexLocation(new HexLocation(0, 0),
                        VertexDirection.East), false);
            case "buildCity":
                return new BuildCityCommand(exchange, 0, new VertexLocation(new HexLocation(0, 0),
                        VertexDirection.East));
            case "offerTrade":
                return new OfferTradeCommand(exchange, 0, new ResourceCards(0, 0, 0, 0, 0), 0);
            case "acceptTrade":
                return new AcceptTradeCommand(exchange, 0, false);
            case "martimeTrade":
                return new MaritimeTradeCommand(exchange, 0, 0, "", "");
            case "discardCards":
                return new DiscardCardsCommand(exchange, 0, new ResourceCards(0, 0, 0, 0, 0));
            default:
                break;
        }
        return null;
    }

}
