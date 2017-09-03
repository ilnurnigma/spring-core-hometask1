package ua.epam.spring.hometask.dao;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.AuditoriumService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TicketDAOTest {
    @Autowired
    private ApplicationContext ctx;

    private TicketDAO ticketDAO;
    private UserDAO userDAO;
    private EventDAO eventDAO;

    @Before
    public void setUp() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(EmbeddedDriver.class.getName());
        dataSource.setUrl("jdbc:derby:memory:db;create=true");

        userDAO = new UserDAO();
        userDAO.setJdbcTemplate(new JdbcTemplate(dataSource));
        userDAO.setTableName("t_user");

        eventDAO = new EventDAO();
        eventDAO.setJdbcTemplate(new JdbcTemplate(dataSource));
        eventDAO.setTableName("t_event");
        eventDAO.setAirdatesTableName("t_airdate");
        eventDAO.setAuditoriumsTableName("t_auditorium");
        eventDAO.setAuditoriumService(ctx.getBean("auditoriumServiceImpl", AuditoriumService.class));

        ticketDAO = new TicketDAO();
        ticketDAO.setJdbcTemplate(new JdbcTemplate(dataSource));
        ticketDAO.setTableName("t_ticket");
        ticketDAO.setUserDAO(userDAO);
        ticketDAO.setEventDAO(eventDAO);

        DBTestHelper.createEventDB(eventDAO.jdbcTemplate);
        DBTestHelper.createUserDB(ticketDAO.jdbcTemplate);
        DBTestHelper.createTicketDB(ticketDAO.jdbcTemplate);
        DBTestHelper.createAirdateDB(eventDAO.jdbcTemplate);
        DBTestHelper.createAuditoriumDB(eventDAO.jdbcTemplate);
    }

    @After
    public void tearDown() throws Exception {
        DBTestHelper.dropDB();
    }

    @Test
    public void save() {
        assumeTrue("Table should be empty before each test", ticketDAO.getAll().isEmpty());

        User user = new User();
        user.setFirstName("John");
        userDAO.save(user);
        user = userDAO.getAll().iterator().next();
        Event event = new Event();
        event = eventDAO.save(event);
        Ticket ticket = new Ticket(user, event, LocalDateTime.now(), 1L);
        ticketDAO.save(ticket);

        assertTrue("Should contain ticket after save: " + ticket, ticketDAO.getAll().contains(ticket));
    }

    @Test
    public void remove() {
        assumeTrue("Table should be empty before each test", ticketDAO.getAll().isEmpty());

        User user = new User();
        user.setFirstName("John");
        userDAO.save(user);
        user = userDAO.getAll().iterator().next();
        Event event = new Event();
        event = eventDAO.save(event);
        Ticket ticket = new Ticket(user, event, LocalDateTime.now(), 1L);
        ticketDAO.save(ticket);

        ticketDAO.remove(ticket);

        assertTrue("Should not contain removed ticket", !ticketDAO.getAll().contains(ticket));
    }

    @Test
    public void getById() {
        assumeTrue("Table should be empty before each test", ticketDAO.getAll().isEmpty());

        User user = new User();
        user.setFirstName("John");
        userDAO.save(user);
        user = userDAO.getAll().iterator().next();
        Event event = new Event();
        event = eventDAO.save(event);
        Ticket ticket = new Ticket(user, event, LocalDateTime.now(), 1L);
        ticketDAO.save(ticket);

        Ticket actualTicket = ticketDAO.getById(ticket.getId());

        assertEquals("Should return ticket by id", ticket, actualTicket);
    }

    @Test
    public void addAll() {
        assumeTrue("Table should be empty before each test", ticketDAO.getAll().isEmpty());

        User user1 = new User();
        user1.setFirstName("John");
        userDAO.save(user1);
        user1 = userDAO.getAll().iterator().next();
        Event event1 = new Event();
        event1 = eventDAO.save(event1);
        Ticket ticket1 = new Ticket(user1, event1, LocalDateTime.now(), 1L);

        User user2 = new User();
        user2.setFirstName("Jane");
        userDAO.save(user2);
        user2 = userDAO.getAll().iterator().next();
        Event event2 = new Event();
        event2 = eventDAO.save(event2);
        Ticket ticket2 = new Ticket(user2, event2, LocalDateTime.now(), 2L);

        HashSet<Ticket> tickets = new HashSet<>();
        tickets.add(ticket1);
        tickets.add(ticket2);

        ticketDAO.addAll(tickets);

        assertTrue("Should contain saved tickets", ticketDAO.getAll().containsAll(tickets));
    }

    @Test
    public void getPurchasedTicketsForEvent() {
        assumeTrue("Table should be empty before each test", ticketDAO.getAll().isEmpty());

        User user = new User();
        user.setFirstName("John");
        userDAO.save(user);
        user = userDAO.getAll().iterator().next();
        Event event = new Event();
        event = eventDAO.save(event);
        LocalDateTime dateTime = LocalDateTime.now();
        Ticket ticket = new Ticket(user, event, dateTime, 1L);
        ticketDAO.save(ticket);

        Set<Ticket> purchasedTickets = ticketDAO.getPurchasedTicketsForEvent(event, dateTime);
        System.out.println(purchasedTickets);

        assertTrue("Should contain purchased ticket", ticketDAO.getAll().containsAll(purchasedTickets));
    }

}