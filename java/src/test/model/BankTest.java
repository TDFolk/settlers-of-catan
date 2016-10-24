package test.model;

import model.Game;
import model.cards_resources.Bank;
import model.cards_resources.DevelopmentCard;
import model.cards_resources.ResourceCards;

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
    Bank myBank = new Bank();


    @Test
    public void canDrawResourceCard() throws Exception {
        System.out.println("Testing can draw resource card\n");

        Game.getInstance().setBank(myBank);

        Game.getInstance().getBank().setResourcePool(new ResourceCards(19,19,19,19,19));
        assertTrue(Game.getInstance().getBank().canDrawResourceCard(ResourceType.BRICK));

        Game.getInstance().getBank().setResourcePool(new ResourceCards(0,19,19,19,19));
        assertFalse(Game.getInstance().getBank().canDrawResourceCard(ResourceType.BRICK));
    }

    @Test
    public void canDrawDevelopmentCard() throws Exception {
        System.out.println("Testing can draw development card\n");

        Game.getInstance().setBank(myBank);

        List<DevelopmentCard> myDevCards = new ArrayList<>();
        DevelopmentCard devCard = new DevelopmentCard(DevCardType.SOLDIER);
        myDevCards.add(devCard);
        Game.getInstance().getBank().setDevelopmentCards(myDevCards);
        assertTrue(Game.getInstance().getBank().canDrawDevelopmentCard());

        myDevCards.clear();
        Game.getInstance().getBank().setDevelopmentCards(myDevCards);
        assertFalse(Game.getInstance().getBank().canDrawDevelopmentCard());
    }

}