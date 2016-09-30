package server;

import java.util.concurrent.ThreadLocalRandom;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bvanc on 9/30/2016.
 */
public class ServerProxyTest {

    IServer proxy;
    String host = "localhost";
    String port = "8081";
    String username;
    String password;
    int infoLength = 30;

    @Before
    public void initialize()
    {
        proxy = new ServerProxy(host, port);
        createRandomUserInfo();
    }

    @Test
    public void userRegister()
    {

    }

    @Test
    public void userLogin()
    {

    }

    public void createRandomUserInfo()
    {
        StringBuilder newName = new StringBuilder();
        StringBuilder newPass = new StringBuilder();

        for(int i = 0; i < infoLength; i++)
        {
            char nextLetter = (char)ThreadLocalRandom.current().nextInt(97,123+1);

            newName.append(nextLetter);
        }

        this.username = newName.toString();

        for(int i = 0; i < infoLength; i++)
        {
            char nextLetter = (char)ThreadLocalRandom.current().nextInt(97,123+1);

            newPass.append(nextLetter);
        }

        this.password = newPass.toString();

        System.out.println(username);
        System.out.println(password);
    }


}
