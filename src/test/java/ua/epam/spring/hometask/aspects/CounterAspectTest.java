package ua.epam.spring.hometask.aspects;

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
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.util.DBCreator;
import ua.epam.spring.hometask.dao.EventCounterDAO;
import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.service.BookingService;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = AppConfig.class)
public class CounterAspectTest {
    @Autowired
    private ApplicationContext ctx;

    private EventDAO eventDAO;
    private CounterAspect counterAspect;

    @Before
    public void setUp() throws Exception {
        eventDAO = ctx.getBean("eventDAO", ua.epam.spring.hometask.dao.EventDAO.class);
        counterAspect = ctx.getBean("counterAspect", CounterAspect.class);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(EmbeddedDriver.class.getName());
        dataSource.setUrl("jdbc:derby:memory:db;create=true");

        JdbcTemplate jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);
        DBCreator.createDB(jdbcTemplate);

        EventCounterDAO eventCounterDAO = new EventCounterDAO();
        eventCounterDAO.setJdbcTemplate(jdbcTemplate);
        eventCounterDAO.setTableName("t_event_counter");

        counterAspect.setEventCounterDAO(eventCounterDAO);
    }

    @After
    public void tearDown() throws Exception {
        DBCreator.dropDB();
    }

    @Test
    public void givenOneAccessToEventNameReturnOne() {
        Event event = new Event();
        event.setName("event1");
        eventDAO.save(event);

        eventDAO.getByName("event1");

        assertEquals(1L, counterAspect.getNameAccessCounter(event));
    }

    @Test
    public void givenTwoTimesAccessToEventNameReturnTwo() {
        Event event = new Event();
        event.setName("event1");
        eventDAO.save(event);

        eventDAO.getByName("event1");
        eventDAO.getByName("event1");

        assertEquals(2L, counterAspect.getNameAccessCounter(event));
    }

    @Test
    public void givenTwoTimesAccessToTwoEventNamesReturnTwoEach() {
        Event event = new Event();
        event.setName("event1");
        event = eventDAO.save(event);
        eventDAO.getByName("event1");
        eventDAO.getByName("event1");

        Event event2 = new Event();
        event2.setName("event2");
        event2 = eventDAO.save(event2);
        eventDAO.getByName("event2");
        eventDAO.getByName("event2");

        assertEquals(2L, counterAspect.getNameAccessCounter(event));
        assertEquals(2L, counterAspect.getNameAccessCounter(event2));
    }

    @Test
    public void givenOneAccessToEventPriceReturnOne() {
        Event event1 = ctx.getBean("event", Event.class);
        event1.setBasePrice(100);
        event1.setId(1L);

        double basePrice = event1.getBasePrice();

        Event event2 = new Event();
        event2.setBasePrice(100);

        assertEquals(1L, counterAspect.getPriceAccessCounter(event1));
    }

    @Test
    public void givenTwoTimesAccessToEventPriceReturnTwo() {
        Event event1 = ctx.getBean("event", Event.class);
        event1.setBasePrice(100);
        event1.setId(1L);

        event1.getBasePrice();
        event1.getBasePrice();

        Event event2 = new Event();
        event2.setId(2L);
        event2.setBasePrice(100);

        assertEquals(2L, counterAspect.getPriceAccessCounter(event1));
    }

    @Test
    public void givenOneAccessToBookTicketsReturnOne() {
        BookingService bookingService = ctx.getBean("bookingServiceImpl", BookingService.class);

        Event event1 = new Event();
        event1.setName("event1");
        eventDAO.save(event1);

        HashSet<Ticket> tickets = new HashSet<>();
        tickets.add(new Ticket(null, event1, LocalDateTime.now(), 1));

        bookingService.bookTickets(tickets);

        assertEquals(1L, counterAspect.getBookTicketCounter(event1));
    }

    @Test
    public void givenTwoAccessToBookTicketsReturnTwo() {
        BookingService bookingService = ctx.getBean("bookingServiceImpl", BookingService.class);

        Event event1 = new Event();
        event1.setName("event1");
        eventDAO.save(event1);

        HashSet<Ticket> tickets = new HashSet<>();
        tickets.add(new Ticket(null, event1, LocalDateTime.now(), 1));
        tickets.add(new Ticket(null, event1, LocalDateTime.now(), 2));

        bookingService.bookTickets(tickets);

        assertEquals(2L, counterAspect.getBookTicketCounter(event1));
    }

    @Test
    public void givenOneAccessToBookTicketReturnOne() {
        BookingService bookingService = ctx.getBean("bookingServiceImpl", BookingService.class);

        Event event1 = new Event();
        event1.setName("event1");
        eventDAO.save(event1);

        Ticket ticket = new Ticket(null, event1, LocalDateTime.now(), 1);

        bookingService.bookTicket(ticket);

        assertEquals(1L, counterAspect.getBookTicketCounter(event1));
    }

    @Test
    public void givenTwoAccessToBookTicketReturnTwo() {
        BookingService bookingService = ctx.getBean("bookingServiceImpl", BookingService.class);

        Event event1 = new Event();
        event1.setName("event1");
        eventDAO.save(event1);

        Ticket ticket = new Ticket(null, event1, LocalDateTime.now(), 1);
        Ticket ticket2 = new Ticket(null, event1, LocalDateTime.now(), 2);

        bookingService.bookTicket(ticket);
        bookingService.bookTicket(ticket2);

        assertEquals(2L, counterAspect.getBookTicketCounter(event1));
    }
}