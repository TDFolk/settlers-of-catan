package server;

import static org.junit.Assert.*;
import java.util.concurrent.ThreadLocalRandom;

import command.game.GameListObject;
import command.game.GameListObjectResult;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
    public void userRegister()
    {   //System.out.println("Registering\nUsername: " + username + " Password: " + password);
        assertTrue(proxy.userRegister(username, password));
    }

    @Test
    public void userLogin()
    {
        //System.out.println("Logging in\nUsername: " + "bob" + " Password: " + "bob");
        assertTrue(proxy.userLogin("bob", "bob"));
    }

    @Test
    public void gameList()
    {
        GameListObjectResult list;
        list = proxy.gameList();
        assertNotNull(list);
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
