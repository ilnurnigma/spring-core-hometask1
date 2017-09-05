package ua.epam.spring.hometask.service.impl;

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
import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created on 8/21/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = AppConfig.class)
public class BookingServiceImplTest {

    @Autowired
    private ApplicationContext ctx;

    private Event event;
    private LocalDateTime dateTime;
    private BookingService service;
    private EventDAO eventDAO;
    private UserDAO userDAO;
    private AuditoriumService auditoriumService;

    @Before
    public void setUp() throws Exception {
        service = ctx.getBean("bookingServiceImpl", BookingService.class);
        eventDAO = ctx.getBean("eventDAO", EventDAO.class);
        userDAO = ctx.getBean("userDAO", UserDAO.class);
        auditoriumService = ctx.getBean("auditoriumServiceImpl", AuditoriumService.class);

        event = new Event();
        event.setBasePrice(100);

        dateTime = LocalDateTime.now();
        Auditorium auditorium = new Auditorium();
        HashSet<Long> vipSeats = new HashSet<>();
        vipSeats.add(5L);
        auditorium.setVipSeats(vipSeats);
        event.addAirDateTime(dateTime, auditorium);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(EmbeddedDriver.class.getName());
        dataSource.setUrl("jdbc:derby:memory:db;create=true");

        JdbcTemplate jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);
        DBCreator.createDB(jdbcTemplate);
    }

    @After
    public void tearDown() throws Exception {
        DBCreator.dropDB();
    }

    @Test
    public void givenApplicationContextReturnBeanImplementation() {
        assertTrue(ctx.getBean("bookingServiceImpl") instanceof BookingService);
    }

    @Test
    public void givenBasePriceReturnBasePrice() throws Exception {
        HashSet<Long> seats = new HashSet<>();
        seats.add(1L);

        double actualPrice = service.getTicketsPrice(event, dateTime, null, seats);

        assertEquals(100, actualPrice, 0);
    }

    @Test
    public void givenBasePriceReturnBasePriceForTwoTickets() throws Exception {
        HashSet<Long> seats = new HashSet<>();
        seats.add(1L);
        seats.add(2L);
        event.assignAuditorium(dateTime, auditoriumService.getByName("Red"));
        event = eventDAO.save(event);

        double actualPrice = service.getTicketsPrice(event, dateTime, null, seats);

        assertEquals(200, actualPrice, 0);
    }

    @Test
    public void givenBasePriceReturnVipPrice() throws Exception {
        HashSet<Long> seats = new HashSet<>();
        seats.add(5L);

        double actualPrice = service.getTicketsPrice(event, dateTime, null, seats);

        assertEquals(200, actualPrice, 0);
    }

    @Test
    public void givenBasePriceAndHighRatedMovieReturnHighRatedPrice() throws Exception {
        event.setRating(EventRating.HIGH);
        HashSet<Long> seats = new HashSet<>();
        seats.add(1L);

        double actualPrice = service.getTicketsPrice(event, dateTime, null, seats);

        assertEquals(120, actualPrice, 0);
    }

    @Test
    public void bookTickets() throws Exception {
        HashSet<Ticket> tickets = new HashSet<>();
        eventDAO.save(event);
        tickets.add(new Ticket(null, event, dateTime, 1L));
        service.bookTickets(tickets);
    }

    @Test
    public void getPurchasedTicketsForEvent() throws Exception {
        HashSet<Ticket> tickets = new HashSet<>();
        event.assignAuditorium(dateTime, auditoriumService.getByName("Red"));
        event = eventDAO.save(event);
        User user = new User();
        user = userDAO.save(user);
        Ticket ticket = new Ticket(user, event, dateTime, 1L);
        tickets.add(ticket);

        service.bookTickets(tickets);

        Set<Ticket> purchasedTickets = service.getPurchasedTicketsForEvent(event, dateTime);

        assertTrue(purchasedTickets.contains(ticket));
    }

}