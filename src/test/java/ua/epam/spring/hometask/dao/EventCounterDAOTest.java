package ua.epam.spring.hometask.dao;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.epam.spring.hometask.domain.Event;

import static org.junit.Assert.*;

public class EventCounterDAOTest {

    private EventCounterDAO eventCounterDAO;

    @Before
    public void setUp() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(EmbeddedDriver.class.getName());
        dataSource.setUrl("jdbc:derby:memory:db;create=true");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        DBTestHelper.createEventCounterDB(jdbcTemplate);

        eventCounterDAO = new EventCounterDAO();
        eventCounterDAO.setJdbcTemplate(jdbcTemplate);
        eventCounterDAO.setTableName("t_event_counter");
    }

    @After
    public void tearDown() throws Exception {
        DBTestHelper.dropDB();
    }

    @Test
    public void saveNameAccessCounter() throws Exception {
        Event event = new Event();
        event.setId(1L);
        eventCounterDAO.addNameAccessCounter(event);
        eventCounterDAO.addNameAccessCounter(event);
        eventCounterDAO.addNameAccessCounter(event);

        assertEquals(3, eventCounterDAO.getNameAccessCounter(event));
    }

    @Test
    public void saveNameAccessCounterTwoEvents() throws Exception {
        Event event1 = new Event();
        event1.setId(1L);
        eventCounterDAO.addNameAccessCounter(event1);
        eventCounterDAO.addNameAccessCounter(event1);
        eventCounterDAO.addNameAccessCounter(event1);

        Event event2 = new Event();
        event2.setId(2L);
        eventCounterDAO.addNameAccessCounter(event2);

        assertEquals(3, eventCounterDAO.getNameAccessCounter(event1));
        assertEquals(1, eventCounterDAO.getNameAccessCounter(event2));
    }

    @Test
    public void savePriceAccessCounter() throws Exception {
        Event event = new Event();
        event.setId(1L);
        eventCounterDAO.addPriceAccesCounter(event);
        eventCounterDAO.addPriceAccesCounter(event);
        eventCounterDAO.addPriceAccesCounter(event);

        assertEquals(3, eventCounterDAO.getPriceAccesCounter(event));
    }

    @Test
    public void savePriceAccessCounterTwoEvents() throws Exception {
        Event event1 = new Event();
        event1.setId(1L);
        eventCounterDAO.addPriceAccesCounter(event1);
        eventCounterDAO.addPriceAccesCounter(event1);
        eventCounterDAO.addPriceAccesCounter(event1);

        Event event2 = new Event();
        event2.setId(2L);
        eventCounterDAO.addPriceAccesCounter(event2);

        assertEquals(3, eventCounterDAO.getPriceAccesCounter(event1));
        assertEquals(1, eventCounterDAO.getPriceAccesCounter(event2));
    }

    @Test
    public void saveBookTicketCounter() throws Exception {
        Event event = new Event();
        event.setId(1L);
        eventCounterDAO.addBookTicketCounter(event);
        eventCounterDAO.addBookTicketCounter(event);
        eventCounterDAO.addBookTicketCounter(event);

        assertEquals(3, eventCounterDAO.getBookTicketCounter(event));
    }

    @Test
    public void saveBookTicketCounterTwoEvents() throws Exception {
        Event event1 = new Event();
        event1.setId(1L);
        eventCounterDAO.addBookTicketCounter(event1);
        eventCounterDAO.addBookTicketCounter(event1);
        eventCounterDAO.addBookTicketCounter(event1);

        Event event2 = new Event();
        event2.setId(2L);
        eventCounterDAO.addBookTicketCounter(event2);

        assertEquals(3, eventCounterDAO.getBookTicketCounter(event1));
        assertEquals(1, eventCounterDAO.getBookTicketCounter(event2));
    }

}