package server;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by bvanc on 9/30/2016.
 */
public class ServerProxyTest {

    IServer proxy;
    String host = "localhost";
    String port = "8081";

    @Before
    public void initialize()
    {
        proxy = new ServerProxy(host, port);
    }




}
