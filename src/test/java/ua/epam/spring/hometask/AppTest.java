package ua.epam.spring.hometask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.dao.DBTestHelper;
import ua.epam.spring.hometask.domain.*;

import java.sql.SQLException;
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
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class AppTest {
    @Autowired
    private ApplicationContext ctx;

    private UserCommand userCommand;
    private AdminCommand adminCommand;

    @Before
    public void setUp() throws SQLException {
        adminCommand = ctx.getBean("adminCommand", AdminCommand.class);
        userCommand = ctx.getBean("userCommand", UserCommand.class);

        JdbcTemplate jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);
        DBTestHelper.createUserDB(jdbcTemplate);

    }

    @After
    public void tearDown() throws Exception {
        DBTestHelper.dropDB();
    }

    @Test
    public void givenEventWhenBoughtShouldBeInPurchasedTickets() {
        Event event = adminCommand.enterEvent("Game of Thrones 7", EventRating.HIGH, 100);
        adminCommand.addAirDateTime(event, LocalDateTime.now().plusDays(5), "Red");

        User user = userCommand.register("John", "Snow", "john_snow@epam.com");
        LocalDateTime dateTime = event.getAirDates().first();
        Ticket ticket = userCommand.buyTicket(user, event, dateTime, 1L);

        Set<Ticket> purchasedTickets = adminCommand.viewPurchasedTickets(event, dateTime);

        assertTrue(purchasedTickets.contains(ticket));
    }

    @Test
    public void givenEventWhenBoughtTwoTicketsShouldBeInPurchasedTickets() {
        Event event = adminCommand.enterEvent("Game of Thrones 7", EventRating.HIGH, 100);
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
        Event event = adminCommand.enterEvent("Game of Thrones 7", EventRating.HIGH, 100);
        adminCommand.addAirDateTime(event, LocalDateTime.now().plusDays(5), "Red");

        userCommand.register("John", "Snow", "john_snow@epam.com");
        Set<Event> events = userCommand.viewEvents(LocalDate.now(), LocalDate.now().plusDays(10));

        assertTrue(events.contains(event));
    }

}