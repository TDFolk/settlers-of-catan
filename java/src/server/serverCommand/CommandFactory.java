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
                return new LoginCommand(exchange);
            case "register":
                return new RegisterCommand(exchange);
            case "list":
                return new ListCommand(exchange);
            case "create":
                return new CreateCommand(exchange);
            case "join":
                return new JoinCommand(exchange);
            case "model":
                return new ModelCommand(exchange);
            case "addAI":
                return new AddAICommand(exchange);
            case "listAI":
                return new ListAICommand(exchange);
            case "sendChat":
                return new SendChatCommand(exchange);
            case "rollNumber":
                return new RollCommand(exchange);
            case "robPlayer":
                return new RobCommand(exchange);
            case "finishTurn":
                return new FinishTurnCommand(exchange);
            case "buyDevCard":
                return new BuyDevCardCommand(exchange);
            case "Year_of_Plenty":
                return new YearOfPlentyCommand(exchange);
            case "Road_Building":
                return new RoadBuildingCommand(exchange);
            case "Soldier":
                return new SoldierCommand(exchange);
            case "Monopoly":
                return new MonopolyCommand(exchange);
            case "Monument":
                return new MonumentCommand(exchange);
            case "buildRoad":
                return new BuildRoadCommand(exchange);
            case "buildSettlement":
                return new BuildSettlementCommand(exchange);
            case "buildCity":
                return new BuildCityCommand(exchange);
            case "offerTrade":
                return new OfferTradeCommand(exchange);
            case "acceptTrade":
                return new AcceptTradeCommand(exchange);
            case "martimeTrade":
                return new MaritimeTradeCommand(exchange);
            case "discardCards":
                return new DiscardCardsCommand(exchange);
            default:
                break;
        }
        return null;
    }

}
