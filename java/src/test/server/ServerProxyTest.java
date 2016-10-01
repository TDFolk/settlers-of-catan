package server;

import static org.junit.Assert.*;
import java.util.concurrent.ThreadLocalRandom;

import command.game.GameCreateObjectResult;
import command.game.GameListObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import shared.definitions.CatanColor;

/**
 * Created by bvanc on 9/30/2016.
 */
public class ServerProxyTest {

    private IServer proxy;
    private String host = "localhost";
    private String port = "8081";
    private String username;
    private String password;
    private int infoLength = 15;

    @Before
    public void setUp()
    {
        proxy = new ServerProxy(host, port);
        createRandomUserInfo();

    }

    @Test
    public void testAll()
    {
        //REGISTER----------------------------------------------------------------------------------------
        assertTrue(proxy.userRegister(username, password));

        //LOGIN-------------------------------------------------------------------------------------------
        assertTrue(proxy.userLogin(username, password));

        //GAME CREATE-------------------------------------------------------------------------------------
        GameCreateObjectResult result;
        result = proxy.gameCreate(true, true, true, "New Game! :D");
        assertNotNull(result);

        //GAME JOIN!!!!-----------------------------------------------------------------------------------
        assertTrue(proxy.gameJoin(result.getId(), "blue"));

        //ADD AI------------------------------------------------------------------------------------------
        //assertTrue(proxy.gameAddAI("LARGEST_ARMY"));

        //SEND CHAT---------------------------------------------------------------------------------------
        String modelJSON;
        modelJSON = proxy.sendChat(0,"Here is my message boiiiiii!");
        assertNotNull(modelJSON);

        //ACCEPT TRADE------------------------------------------------------------------------------------
        
    }

    @Test
    public void gameList()
    {
        GameListObject list[];
        list = proxy.gameList();
        assertNotNull(list);
    }

    @Test
    public void gameListAI()
    {
        String list;
        list = proxy.gameListAI();
        assertNotNull(list);
    }

    @Test
    public void gameAddAI()
    {

    }



    public void createRandomUserInfo()
    {
        StringBuilder newName = new StringBuilder();
        StringBuilder newPass = new StringBuilder();

        for(int i = 0; i < infoLength; i++)
        {
            char nextLetter = (char)ThreadLocalRandom.current().nextInt(97,122+1);

            newName.append(nextLetter);
        }

        this.username = newName.toString();

        for(int i = 0; i < infoLength; i++)
        {
            char nextLetter = (char)ThreadLocalRandom.current().nextInt(97,122+1);

            newPass.append(nextLetter);
        }

        this.password = newPass.toString();

    }


}
