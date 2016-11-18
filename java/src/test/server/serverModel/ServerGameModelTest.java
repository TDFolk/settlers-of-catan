package test.server.serverModel;

import org.junit.Test;

import server.serverModel.ServerGameModel;
import static org.junit.Assert.*;

/**
 * Created by bvanc on 11/16/2016.
 */
public class ServerGameModelTest {

    ServerGameModel model;

    @Test
    public void gameModel() throws Exception {
        model = new ServerGameModel(0);

        String json = model.gameModel();
        assertNotNull(json);
    }

    private String example_model = "{\"deck\":{\"yearOfPlenty\":2," +
            "\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5}," +
            "\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}}," +
            "{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":-2}," +
            "\"number\":4},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2}," +
            "\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":-1}," +
            "\"number\":8},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":-1},\"number\":3}" +
            ",{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-1},\"number\":9}," +
            "{\"resource\":\"sheep\",\"location\":{\"x\":2,\"y\":-1},\"number\":12}," +
            "{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":0},\"number\":5}," +
            "{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":10}," +
            "{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":0},\"number\":11}," +
            "{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":0},\"number\":5}," +
            "{\"resource\":\"wheat\",\"location\":{\"x\":2,\"y\":0},\"number\":6}," +
            "{\"resource\":\"wheat\",\"location\":{\"x\":-2,\"y\":1},\"number\":2}," +
            "{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":1},\"number\":9}," +
            "{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":1},\"number\":4}," +
            "{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":1},\"number\":10}," +
            "{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":2},\"number\":6}," +
            "{\"resource\":\"ore\",\"location\":{\"x\":-1,\"y\":2},\"number\":3}," +
            "{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":8}]," +
            "\"roads\":[]," +
            "\"cities\":[]," +
            "\"settlements\":[]," +
            "\"radius\":3," +
            "\"ports\":[{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}}," +
            "{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}}," +
            "{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}}," +
            "{\"ratio\":3,\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}}," +
            "{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}}," +
            "{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}}," +
            "{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}}," +
            "{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}}," +
            "{\"ratio\":3,\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}}]," +
            "\"robber\":{\"x\":0,\"y\":-2}}," +
            "\"players\":[{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0}," +
                "\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0}," +
                "\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0}," +
                "\"roads\":15," +
                "\"cities\":4," +
                "\"settlements\":5," +
                "\"soldiers\":0," +
                "\"victoryPoints\":0," +
                "\"monuments\":0," +
                "\"playedDevCard\":false," +
                "\"discarded\":false," +
                "\"playerID\":12," +
                "\"playerIndex\":0" +
                ",\"name\":\"aaa\",\"color\":\"green\"}" +
            ",null,null,null]," +
            "\"log\":{\"lines\":[]}," +
            "\"chat\":{\"lines\":[]}," +
            "\"bank\":{\"brick\":24,\"wood\":24,\"sheep\":24,\"wheat\":24,\"ore\":24}," +
            "\"turnTracker\":{\"status\":\"FirstRound\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":0}";
}