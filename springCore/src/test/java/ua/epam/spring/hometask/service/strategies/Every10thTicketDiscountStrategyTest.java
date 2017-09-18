package ua.epam.spring.hometask.service.strategies;

import org.junit.Before;
import org.junit.Test;
import ua.epam.spring.hometask.domain.Event;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * Created on 8/21/2017.
 */
public class Every10thTicketDiscountStrategyTest {

    private DiscountStrategy strategy;

    @Before
    public void setUp() throws Exception {
        strategy = new Every10thTicketDiscountStrategy();
    }

    @Test
    public void givenFirstTicketReturnZero() throws Exception {
        byte discount = strategy.getDiscount(null, new Event(), LocalDateTime.now(), 1L);
        assertEquals(0, discount);
    }

    @Test
    public void given100TicketReturn5() throws Exception {
        byte discount = strategy.getDiscount(null, new Event(), LocalDateTime.now(), 100L);
        assertEquals(5, discount);
    }

    @Test
    public void given200TicketReturn10() throws Exception {
        byte discount = strategy.getDiscount(null, new Event(), LocalDateTime.now(), 200L);
        assertEquals(5, discount);
    }

    @Test
    public void given11TicketReturn5() throws Exception {
        byte discount = strategy.getDiscount(null, new Event(), LocalDateTime.now(), 11L);
        assertEquals(5, discount);
    }

    @Test
    public void given12TicketReturn4() throws Exception {
        byte discount = strategy.getDiscount(null, new Event(), LocalDateTime.now(), 12L);
        assertEquals(4, discount);
    }

}