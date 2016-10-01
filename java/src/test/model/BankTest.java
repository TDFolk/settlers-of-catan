package model;

import model.development_cards.DevelopmentCard;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Trent on 9/30/2016.
 */
public class BankTest {

    @Test
    public void canDrawResourceCard() throws Exception {
        System.out.println("Testing can draw resource card\n");

        assertTrue(Game.getInstance().getBank().canDrawResourceCard(ResourceType.BRICK));

        Game.getInstance().getBank().setResourcePool(new ResourceCards(0,19,19,19,19));
        assertFalse(Game.getInstance().getBank().canDrawResourceCard(ResourceType.BRICK));
    }

    @Test
    public void canDrawDevelopmentCard() throws Exception {
        System.out.println("Testing can draw development card\n");

        assertTrue(Game.getInstance().getBank().canDrawDevelopmentCard());

        List<DevelopmentCard> myDevCards = new ArrayList<>();
        Game.getInstance().getBank().setDevelopmentCards(myDevCards);
        assertFalse(Game.getInstance().getBank().canDrawDevelopmentCard());
    }

}