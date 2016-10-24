package test.decoder;

import model.Facade;
import org.junit.Before;
import org.junit.Test;
import server.IServer;
import server.MockServer;
import server.ServerPoller;

import static org.junit.Assert.*;

/**
 * Created by bvanc on 10/3/2016.
 */
public class DecoderTest {

    IServer mock;


    @Before
    public void initialize() {
        mock = new MockServer("","");

    }

    @Test
    public void decoderTest()
    {
        String jsonModel = mock.gameModelVersion(0);

        Facade.getInstance().replaceModel(jsonModel);
    }




}