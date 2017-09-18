package ua.epam.spring.hometask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.util.DBCreator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        JdbcOperations jdbcTemplate = ctx.getBean("jdbcTemplate", JdbcOperations.class);
//        DBCreator.createDB(jdbcTemplate);
    }

    @After
    public void tearDown() throws Exception {
        DBCreator.dropDB();
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

}