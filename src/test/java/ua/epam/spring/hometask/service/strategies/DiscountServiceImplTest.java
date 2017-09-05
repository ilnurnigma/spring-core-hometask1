package ua.epam.spring.hometask.service.strategies;

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
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = AppConfig.class)
public class DiscountServiceImplTest {
    @Autowired
    private ApplicationContext ctx;

    private DiscountService discountService;

    @Before
    public void setUp() throws Exception {
        discountService = ctx.getBean("discountService", DiscountService.class);
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
    public void givenOneTicketShouldReturnZero() throws Exception {
        byte discount = discountService.getDiscount(null, new Event(), LocalDateTime.now(), 1L);
        assertEquals(0, discount);

    }

    @Test
    public void given10TicketsShouldReturn5() throws Exception {
        byte discount = discountService.getDiscount(null, new Event(), LocalDateTime.now(), 10L);
        assertEquals(5, discount);

    }

    @Test
    public void given12TicketsShouldReturn4() throws Exception {
        byte discount = discountService.getDiscount(null, new Event(), LocalDateTime.now(), 12L);
        assertEquals(4, discount);

    }

    @Test
    public void given12TicketsAndBirthdayWithin4DaysShouldReturn5() throws Exception {
        User user = new User();
        user.setDateOfBirth(LocalDate.now().plusDays(4));

        byte discount = discountService.getDiscount(user, new Event(), LocalDateTime.now(), 12L);

        assertEquals(5, discount);

    }

}