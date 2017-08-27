package ua.epam.spring.hometask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created on 8/23/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = AppConfig.class)
public class AppTest {
    @Autowired
    private ApplicationContext ctx;

    private UserCommand userCommand;
    private AdminCommand adminCommand;

    @Before
    public void setUp() {
        adminCommand = ctx.getBean("adminCommand", AdminCommand.class);
        userCommand = ctx.getBean("userCommand", UserCommand.class);
    }

    @Test
    public void givenEventWhenBoughtShouldBeInPurchasedTickets() {
        Event event = getEvent("Game of Thrones 7", EventRating.HIGH, 100);
        event = adminCommand.enterEvent(event);
        adminCommand.addAirDateTime(event, LocalDateTime.now().plusDays(5), "Red");

        User user = userCommand.register("John", "Snow", "john_snow@epam.com");
        LocalDateTime dateTime = event.getAirDates().first();
        Ticket ticket = userCommand.buyTicket(user, event, dateTime, 1L);

        Set<Ticket> purchasedTickets = adminCommand.viewPurchasedTickets(event, dateTime);

        assertTrue(purchasedTickets.contains(ticket));
    }

    private Event getEvent(String name, EventRating rating, double price) {
        Event event = ctx.getBean("event", Event.class);
        event.setName(name);
        event.setRating(rating);
        event.setBasePrice(price);
        return event;
    }

    @Test
    public void givenEventWhenBoughtTwoTicketsShouldBeInPurchasedTickets() {
        Event event = getEvent("Game of Thrones 7", EventRating.HIGH, 100);
        event = adminCommand.enterEvent(event);
        adminCommand.addAirDateTime(event, LocalDateTime.now().plusDays(5), "Red");

        User user = userCommand.register("John", "Snow", "john_snow@epam.com");

        LocalDateTime dateTime = event.getAirDates().first();
        HashSet<Ticket> tickets = new HashSet<>();
        tickets.add(new Ticket(user, event, dateTime, 1L));
        tickets.add(new Ticket(user, event, dateTime, 2L));
        userCommand.buyTickets(tickets);

        Set<Ticket> purchasedTickets = adminCommand.viewPurchasedTickets(event, dateTime);

        assertTrue(purchasedTickets.containsAll(tickets));
    }

    @Test
    public void givenEventUserShouldBeAbleToViewEvents() {
        Event event = getEvent("Game of Thrones 7", EventRating.HIGH, 100);
        event = adminCommand.enterEvent(event);
        adminCommand.addAirDateTime(event, LocalDateTime.now().plusDays(5), "Red");

        userCommand.register("John", "Snow", "john_snow@epam.com");
        Set<Event> events = userCommand.viewEvents(LocalDate.now(), LocalDate.now().plusDays(10));

        assertTrue(events.contains(event));
    }

}