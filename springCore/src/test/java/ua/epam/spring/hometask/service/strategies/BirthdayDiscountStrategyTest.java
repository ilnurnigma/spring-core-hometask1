package ua.epam.spring.hometask.service.strategies;

import org.junit.Before;
import org.junit.Test;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class BirthdayDiscountStrategyTest {
    private DiscountStrategy strategy;

    @Before
    public void setUp() throws Exception {
        strategy = new BirthdayDiscountStrategy();
    }

    @Test
    public void givenBirthdayWithin5daysOfAirdateShouldReturn5() throws Exception {
        User user = new User();
        user.setDateOfBirth(LocalDate.now().plusDays(4));

        byte discount = strategy.getDiscount(user, new Event(), LocalDateTime.now(), 1L);

        assertEquals(5, discount);
    }

    @Test
    public void givenBirthdayWithin10daysOfAirdateShouldReturnZero() throws Exception {
        User user = new User();
        user.setDateOfBirth(LocalDate.now().plusDays(10));

        byte discount = strategy.getDiscount(user, new Event(), LocalDateTime.now(), 1L);

        assertEquals(0, discount);
    }

}