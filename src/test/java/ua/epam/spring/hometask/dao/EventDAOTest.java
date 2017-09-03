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
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.impl.AuditoriumServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class EventDAOTest {
    @Autowired
    private ApplicationContext ctx;

    private EventDAO eventDAO;

    @Before
    public void setUp() throws Exception {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(EmbeddedDriver.class.getName());
        dataSource.setUrl("jdbc:derby:memory:db;create=true");

        eventDAO = new EventDAO();
        eventDAO.setJdbcTemplate(new JdbcTemplate(dataSource));
        eventDAO.setTableName("t_event");
        eventDAO.setAirdatesTableName("t_airdate");
        eventDAO.setAuditoriumsTableName("t_auditorium");
        eventDAO.setAuditoriumService(ctx.getBean("auditoriumServiceImpl", AuditoriumService.class));

        DBTestHelper.createEventDB(eventDAO.jdbcTemplate);
        DBTestHelper.createAirdateDB(eventDAO.jdbcTemplate);
        DBTestHelper.createAuditoriumDB(eventDAO.jdbcTemplate);
    }

    @After
    public void tearDown() throws Exception {
        DBTestHelper.dropDB();
    }

    @Test
    public void save() {
        assumeTrue("Table should be empty before each test", eventDAO.getAll().isEmpty());

        Event event = new Event();
        event.setName("event1");
        event.setBasePrice(100);
        event.setRating(EventRating.HIGH);

        eventDAO.save(event);

        System.out.println(eventDAO.getAll());
        assertTrue("Should contain saved event: " + event, eventDAO.getAll().contains(event));
    }

    @Test
    public void saveBasePrice() {
        assumeTrue("Table should be empty before each test", eventDAO.getAll().isEmpty());

        Event event = new Event();
        event.setName("event1");
        event.setBasePrice(100);
        event.setRating(EventRating.HIGH);

        eventDAO.save(event);

        double basePrice = eventDAO.getAll().iterator().next().getBasePrice();
        assertEquals("Base price should be saved", 100, basePrice, 0);
    }

    @Test
    public void saveRating() {
        assumeTrue("Table should be empty before each test", eventDAO.getAll().isEmpty());

        Event event = new Event();
        event.setName("event1");
        event.setRating(EventRating.HIGH);

        eventDAO.save(event);

        EventRating rating = eventDAO.getAll().iterator().next().getRating();
        assertEquals("Rating should be saved", EventRating.HIGH, rating);
    }

    @Test
    public void saveAirdates() {
        assumeTrue("Table should be empty before each test", eventDAO.getAll().isEmpty());

        Event event = new Event();
        event.setName("event1");
        TreeSet<LocalDateTime> airDates = new TreeSet<>();
        airDates.add(LocalDateTime.now());
        event.setAirDates(airDates);

        eventDAO.save(event);

        NavigableSet<LocalDateTime> actualAirDates = eventDAO.getAll().iterator().next().getAirDates();
        assertTrue("Airdates should be saved", actualAirDates.containsAll(airDates));
    }

    @Test
    public void saveAuditoriums() {
        assumeTrue("Table should be empty before each test", eventDAO.getAll().isEmpty());

        Event event = new Event();
        TreeMap<LocalDateTime, Auditorium> auditoriumTreeMap = new TreeMap<>();
        Auditorium auditorium = new Auditorium();
        auditorium.setName("Red");
        LocalDateTime dateTime = LocalDateTime.now();
        auditoriumTreeMap.put(dateTime, auditorium);
        event.setAuditoriums(auditoriumTreeMap);

        eventDAO.save(event);

        NavigableMap<LocalDateTime, Auditorium> auditoriums = eventDAO.getAll().iterator().next().getAuditoriums();
        System.out.println(auditoriums);
        assertEquals("Auditoriums should be saved", auditorium, auditoriums.get(dateTime));
    }

    @Test
    public void remove() {
        assumeTrue("Table should be empty before each test", eventDAO.getAll().isEmpty());

        Event event = new Event();
        eventDAO.save(event);

        eventDAO.remove(event);

        assertTrue("Should not contain removed event", !eventDAO.getAll().contains(event));
    }

    @Test
    public void getById() {
        assumeTrue("Table should be empty before each test", eventDAO.getAll().isEmpty());

        Event event = new Event();
        event.setName("event1");
        eventDAO.save(event);
        event = eventDAO.getAll().iterator().next();

        assertEquals(event, eventDAO.getById(event.getId()));
    }

    @Test
    public void getByName() {
        assumeTrue("Table should be empty before each test", eventDAO.getAll().isEmpty());

        Event event = new Event();
        event.setName("event1");
        eventDAO.save(event);
        event = eventDAO.getAll().iterator().next();

        assertEquals(event, eventDAO.getByName(event.getName()));
    }

    @Test
    public void getForDateRange() {
        assumeTrue("Table should be empty before each test", eventDAO.getAll().isEmpty());

        Event event = new Event();
        event.setName("event1");
        TreeSet<LocalDateTime> airDates = new TreeSet<>();
        airDates.add(LocalDateTime.now().plusDays(2));
        event.setAirDates(airDates);
        eventDAO.save(event);

        Set<Event> forDateRange = eventDAO.getForDateRange(LocalDate.now(), LocalDate.now().plusDays(5));
        System.out.println(forDateRange);
        assertTrue("Should return event that is inside time period",forDateRange.contains(event));
    }


}