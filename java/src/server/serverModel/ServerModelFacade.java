package server.serverModel;

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

    public String acceptTrade(){return "model";}

    public String buildCity(){return "model";}

    public String buildRoad(){return "model";}

    public String buildSettlement(){return "model";}

    public String buyDevCard(){return "model";}

    public String discardCards(){return "model";}

    public String finishTurn(){return "model";}

    public String maritimeTrade(){return "model";}

    public String monopoly(){return "model";}

    public String monument(){return "model";}

    public String offerTrade(){return "model";}

    public String roadBuilding(){return "model";}

    public String rob(){return "model";}

    public String roll(){return "model";}

    public String sendChat(){return "model";}

    public String soldier(){return "model";}

    public String yearOfPlenty(){return "model";}

    public boolean userLogin(){return false;}

    public boolean registerUser(String userName, String password){return false;}

    public String listAI(){return "list";}

    public boolean createGame(){return false;}

    public String listGames(){return "list";}

}
