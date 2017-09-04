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
import ua.epam.spring.hometask.aspects.CounterAspect;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.dao.DBTestHelper;
import ua.epam.spring.hometask.dao.EventCounterDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.EventService;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = AppConfig.class)
public class EventServiceImplTest {
    @Autowired
    private ApplicationContext ctx;

    @Before
    public void setUp() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(EmbeddedDriver.class.getName());
        dataSource.setUrl("jdbc:derby:memory:db;create=true");

        JdbcTemplate jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcTemplate.class);
        DBTestHelper.createDB(jdbcTemplate);
    }

    @After
    public void tearDown() throws Exception {
        DBTestHelper.dropDB();
    }

    @Test
    public void givenEventReturnByName() throws Exception {
        EventService eventService = ctx.getBean("eventServiceImpl", EventService.class);
        Event givenEvent = new Event();
        givenEvent.setName("BattleEvent");
        eventService.save(givenEvent);

        Event returnedEvent = eventService.getByName("BattleEvent");

        assertEquals(givenEvent, returnedEvent);
    }

}