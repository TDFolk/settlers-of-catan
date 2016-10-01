package server;

import model.Facade;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Trent on 9/27/2016.
 *
 * */

public class ServerPollerTest {
    IServer mock;
    ServerPoller test;

    @Before
    public void initialize() {
        mock = new MockServer("","");
        test = new ServerPoller(mock);
    }

    @Test
    public void startPoller() throws Exception {
        test.startPoller();
        System.out.println("Testing Start Poller...\n");
        Thread.sleep(3000);
        assertNotNull(test.getCurrentModel());
    }

    @Test
    public void stopPoller() throws Exception {
        System.out.println("Testing Stop Poller\n");
        test.stopPoller();
        assertTrue(true);
    }

}
