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
import ua.epam.spring.hometask.dao.DBTestHelper;
import ua.epam.spring.hometask.dao.DiscountCounterDAO;
import ua.epam.spring.hometask.dao.EventCounterDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.strategies.BirthdayDiscountStrategy;
import ua.epam.spring.hometask.service.strategies.DiscountStrategy;
import ua.epam.spring.hometask.service.strategies.Every10thTicketDiscountStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = AppConfig.class)
public class DiscountAspectTest {

    @Autowired
    private ApplicationContext ctx;
    private DiscountStrategy every10thDiscountStrategy;
    private DiscountAspect discountAspect;
    private DiscountStrategy birthdayDiscountStrategy;

    @Before
    public void setUp() throws Exception {
        discountAspect = ctx.getBean("discountAspect", DiscountAspect.class);
        every10thDiscountStrategy = ctx.getBean("every10thDiscountStrategy", DiscountStrategy.class);
        birthdayDiscountStrategy = ctx.getBean("birthdayDiscountStrategy", DiscountStrategy.class);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(EmbeddedDriver.class.getName());
        dataSource.setUrl("jdbc:derby:memory:db;create=true");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        DBTestHelper.createDiscountCounterDB(jdbcTemplate);

        DiscountCounterDAO discountCounterDAO = new DiscountCounterDAO();
        discountCounterDAO.setJdbcTemplate(jdbcTemplate);
        discountCounterDAO.setTableName("t_discount_counter");

        discountAspect.setDiscountCounterDAO(discountCounterDAO);
    }

    @After
    public void tearDown() throws Exception {
        DBTestHelper.dropDB();
    }

    @Test
    public void givenEvery10thTicketDiscountStrategyWithOneTicketShouldReturnZero() {
        every10thDiscountStrategy.getDiscount(null, new Event(), LocalDateTime.now(), 1);

        assertEquals(1, discountAspect.getTotalDiscountCounter(Every10thTicketDiscountStrategy.class));
    }

    @Test
    public void givenEvery10thTicketDiscountStrategyWithTenTicketsShouldReturnOne() {
        every10thDiscountStrategy.getDiscount(null, new Event(), LocalDateTime.now(), 10);

        assertEquals(1, discountAspect.getTotalDiscountCounter(Every10thTicketDiscountStrategy.class));
    }

    @Test
    public void givenTwoDiscountStrategiesShouldReturnTwo() {
        every10thDiscountStrategy.getDiscount(null, new Event(), LocalDateTime.now(), 10);
        User user = new User();
        user.setDateOfBirth(LocalDate.now());
        user.setId(1L);
        birthdayDiscountStrategy.getDiscount(user, new Event(), LocalDateTime.now(), 1);

        assertEquals(1, discountAspect.getTotalDiscountCounter(Every10thTicketDiscountStrategy.class));
        assertEquals(1, discountAspect.getTotalDiscountCounter(BirthdayDiscountStrategy.class));
    }

    @Test
    public void givenEvery10thTicketDiscountStrategyForUserWithTenTicketsShouldReturnOne() {
        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Snow");
        user1.setId(1L);

        every10thDiscountStrategy.getDiscount(user1, new Event(), LocalDateTime.now(), 10);

        assertEquals(1, discountAspect.getDiscountCounterForUser(Every10thTicketDiscountStrategy.class, user1));
    }

    @Test
    public void givenStrategyForTwoUsers() {
        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Snow");
        user1.setId(1L);

        User user2 = new User();
        user2.setFirstName("Barry");
        user2.setId(2L);

        every10thDiscountStrategy.getDiscount(user1, new Event(), LocalDateTime.now(), 10);
        every10thDiscountStrategy.getDiscount(user2, new Event(), LocalDateTime.now(), 10);

        assertEquals(1, discountAspect.getDiscountCounterForUser(Every10thTicketDiscountStrategy.class, user1));
        assertEquals(1, discountAspect.getDiscountCounterForUser(Every10thTicketDiscountStrategy.class, user2));
        assertEquals(2, discountAspect.getTotalDiscountCounter(Every10thTicketDiscountStrategy.class));
    }
}