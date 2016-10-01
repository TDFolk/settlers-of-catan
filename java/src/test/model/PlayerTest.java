package model;

<<<<<<< HEAD
import model.development_cards.DevelopmentCard;
import model.development_cards.RoadBuildingCard;
import model.map.GeneralPort;
import model.map.Port;
=======
import model.cards_resources.DevelopmentCard;
import model.cards_resources.ResourceCards;
>>>>>>> origin/master
import org.junit.Before;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by bvanc on 9/30/2016.
 */
public class PlayerTest {
    Player myPlayer;

    @Before
    public void init() {
        myPlayer = new Player();
    }

    @Test
    public void canBuyRoad() throws Exception {
        System.out.println("Testing can buy road\n");

        myPlayer.setResourceCards(new ResourceCards(1,0,0,0,1));
        assertTrue(myPlayer.canBuyRoad());

        myPlayer.setResourceCards(new ResourceCards(0,3,2,1,2));
        assertFalse(myPlayer.canBuyRoad());
    }

    @Test
    public void canBuySettlement() throws Exception {
        System.out.println("Testing can buy settlement\n");

        myPlayer.setResourceCards(new ResourceCards(1,0,1,1,1));
        assertTrue(myPlayer.canBuySettlement());

        myPlayer.setResourceCards(new ResourceCards(1,0,0,0,1));
        assertFalse(myPlayer.canBuySettlement());
    }

    @Test
    public void canBuyCity() throws Exception {
        System.out.println("Testing can buy city\n");

        myPlayer.setResourceCards(new ResourceCards(0,3,0,2,0));
        assertTrue(myPlayer.canBuyCity());

        myPlayer.setResourceCards(new ResourceCards(0,0,0,0,0));
        assertFalse(myPlayer.canBuyCity());
    }

    @Test
    public void canBuyDevelopmentCard() throws Exception {
        System.out.println("Testing can buy development card\n");

        myPlayer.setResourceCards(new ResourceCards(0,1,1,1,0));
        assertTrue(myPlayer.canBuyDevelopmentCard());

        myPlayer.setResourceCards(new ResourceCards(3,2,3,0,2));
        assertFalse(myPlayer.canBuyDevelopmentCard());
    }

    @Test
    public void canPlayDevelopmentCard() throws Exception {
        System.out.println("Testing can play development card\n");

        List<DevelopmentCard> devCards = new ArrayList<DevelopmentCard>();
        DevelopmentCard singleDevCard = new DevelopmentCard(DevCardType.ROAD_BUILD);
        devCards.add(singleDevCard);
        myPlayer.setDevelopmentCards(devCards);

        assertTrue(myPlayer.canPlayDevelopmentCard(DevCardType.ROAD_BUILD));
        assertFalse(myPlayer.canPlayDevelopmentCard(DevCardType.MONOPOLY));
    }

    @Test
    public void canDiscard() throws Exception {
        System.out.println("Testing can discard\n");

        myPlayer.setResourceCards(new ResourceCards(1,2,3,2,1));
        assertTrue(myPlayer.canDiscard(new ResourceCards(1,1,1,1,1)));
        assertFalse(myPlayer.canDiscard(new ResourceCards(2,1,1,1,1)));
    }

    @Test
    public void canPlaceSettlement() throws Exception {
        System.out.println("Testing can place settlement\n");

        HexLocation hLoc = new HexLocation(-1,2);
        VertexLocation vLoc = new VertexLocation(hLoc,VertexDirection.East);
        assertTrue(myPlayer.canPlaceSettlement(vLoc));
    }

    @Test
    public void canPlaceRoad() throws Exception {
        System.out.println("Testing can place road\n");

        HexLocation hLoc = new HexLocation(-1,2);
        EdgeLocation eLoc = new EdgeLocation(hLoc, EdgeDirection.South);
        assertTrue(myPlayer.canPlaceRoad(eLoc));
    }

    @Test
    public void canMakeTrade() throws Exception {
        System.out.println("Testing can make trade\n");

        myPlayer.setResourceCards(new ResourceCards(2,2,0,2,2));
        assertTrue(myPlayer.canMakeTrade(new ResourceCards(1,0,0,0,0)));
        assertFalse(myPlayer.canMakeTrade(new ResourceCards(0,0,1,0,0)));
    }

    @Test
    public void canPortTrade() throws Exception {
        System.out.println("Testing can port trade\n");

        List<Port> ports = new ArrayList<>();
        HexLocation hLoc = new HexLocation(-1,2);
        EdgeLocation eLoc = new EdgeLocation(hLoc, EdgeDirection.South);

        Port myPort = new GeneralPort(eLoc, ResourceType.BRICK);
        ports.add(myPort);
        Game.getInstance().getMap().setPorts(ports);

        assertTrue(myPlayer.canPortTrade(eLoc));
    }
}