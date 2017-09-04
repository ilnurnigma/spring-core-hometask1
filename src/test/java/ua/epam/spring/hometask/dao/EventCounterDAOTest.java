package ua.epam.spring.hometask.dao;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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
    }

    @After
    public void tearDown() throws Exception {
        DBTestHelper.dropDB();
    }

    @Test
    public void save() throws Exception {
        eventCounterDAO.addNameAccessCounter();
        eventCounterDAO.addNameAccessCounter();
        eventCounterDAO.addNameAccessCounter();

        assertEquals(3, eventCounterDAO.getNameAccessCounter());
    }

}