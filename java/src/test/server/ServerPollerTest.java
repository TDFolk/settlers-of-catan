package server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Tanner on 9/27/2016.
 */
public class ServerPollerTest {
    @Test
    public void startPoller() throws Exception {
        IServer mock = new MockServer("","");
        ServerPoller test = new ServerPoller(mock);
        test.startPoller();
    }

    @Test
    public void stopPoller() throws Exception {

    }

}