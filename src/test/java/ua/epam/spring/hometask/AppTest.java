package ua.epam.spring.hometask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.config.WebConfig;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.util.DBCreator;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
    public void setUp() throws SQLException {
        adminCommand = ctx.getBean("adminCommand", AdminCommand.class);
        userCommand = ctx.getBean("userCommand", UserCommand.class);

        JdbcTemplate jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);
//        DBCreator.createDB(jdbcTemplate);
    }

    @After
    public void tearDown() throws Exception {
        DBCreator.dropDB();
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
        System.out.println(event);
        System.out.println(events);
        assertTrue(events.contains(event));
    }

    @Test
    public void marshall() throws FileNotFoundException {
        FileOutputStream outputStream = new FileOutputStream("users.xml");
        User user1 = new User();
        user1.setFirstName("John");

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(new Class[]{ua.epam.spring.hometask.domain.User.class});
        marshaller.setMarshallerProperties(new HashMap<String, Object>() {{
            put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
        }});
        marshaller.marshal(user1, new StreamResult(outputStream));

        Object user = marshaller.unmarshal(new StreamSource(new FileInputStream("users.xml")));
        System.out.println(user);
    }
}