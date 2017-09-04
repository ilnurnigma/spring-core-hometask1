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
import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = AppConfig.class)
public class LuckyWinnerAspectTest {
    @Autowired
    private ApplicationContext ctx;

    private BookingService bookingService;
    private LuckyWinnerAspect luckyWinnerAspect;
    private EventDAO eventDAO;
    private UserDAO userDAO;

    @Before
    public void setUp() throws Exception {
        bookingService = ctx.getBean("bookingServiceImpl", BookingService.class);
        luckyWinnerAspect = ctx.getBean("luckyWinnerAspect", LuckyWinnerAspect.class);
        eventDAO = ctx.getBean("eventDAO", ua.epam.spring.hometask.dao.EventDAO.class);
        userDAO = ctx.getBean("userDAO", UserDAO.class);

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
    public void testBasePriceToZero() {
        Event event = new Event();
        event.setBasePrice(100);
        event = eventDAO.save(event);

        luckyWinnerAspect.setLuckChecker(ticket -> true);

        bookingService.bookTicket(new Ticket(null, event, LocalDateTime.now(), 1L));

        assertEquals(0, event.getBasePrice(), 0);
    }

    @Test
    public void testBasePriceNotChanged() {
        Event event = new Event();
        event.setBasePrice(100);
        event = eventDAO.save(event);

        luckyWinnerAspect.setLuckChecker(ticket -> false);

        bookingService.bookTicket(new Ticket(null, event, LocalDateTime.now(), 1L));

        assertEquals(100, event.getBasePrice(), 0);
    }

    @Test
    public void luckyEventMsg() {
        Event event = new Event();
        event.setBasePrice(100);
        event = eventDAO.save(event);

        User user = new User();
        user.setFirstName("John");
        userDAO.save(user);

        luckyWinnerAspect.setLuckChecker(ticket -> true);

        Ticket ticket = new Ticket(user, event, LocalDateTime.now(), 1L);
        bookingService.bookTicket(ticket);

        System.out.println(user.getSystemMessages());

        assertEquals("Lucky event: zero price on ticket: " + ticket + " !", user.getSystemMessages().get(0));
    }
}