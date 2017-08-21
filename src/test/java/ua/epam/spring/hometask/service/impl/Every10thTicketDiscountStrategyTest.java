package ua.epam.spring.hometask.service.impl;

import org.junit.Before;
import org.junit.Test;
import ua.epam.spring.hometask.domain.Event;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created on 8/21/2017.
 */
public class Every10thTicketDiscountStrategyTest {

    private Every10thTicketDiscountStrategy every10thTicketDiscountStrategy;

    @Before
    public void setUp() throws Exception {
        every10thTicketDiscountStrategy = new Every10thTicketDiscountStrategy();
    }

    @Test
    public void givenFirstTicketReturnZero() throws Exception {
        byte discount = every10thTicketDiscountStrategy.getDiscount(null, new Event(), LocalDateTime.now(), 1L);
        assertEquals(0, discount);
    }

    @Test
    public void givenTenthTicketReturn50() throws Exception {
        byte discount = every10thTicketDiscountStrategy.getDiscount(null, new Event(), LocalDateTime.now(), 10L);
        assertEquals(50, discount);
    }

    @Test
    public void givenUserWithBirthdayReturn5() throws Exception {
        byte discount = every10thTicketDiscountStrategy.getDiscount(null, new Event(), LocalDateTime.now(), 10L);
        assertEquals(50, discount);
    }

}