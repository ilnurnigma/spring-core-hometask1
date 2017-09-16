package ua.epam.spring.hometask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.epam.spring.hometask.dao.*;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.*;
import ua.epam.spring.hometask.service.impl.*;
import ua.epam.spring.hometask.util.DBCreator;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableTransactionManagement
@Import(DiscountServiceBeansConfig.class)
@PropertySource({"classpath:auditorium.properties", "classpath:db.properties"})
public class ServiceBeansConfig {
    @Autowired
    DiscountService discountService;

    @Value("${a1.name}")
    private String auditorium1Name;

    @Value("${a2.name}")
    private String auditorium2Name;

    @Value("${a3.name}")
    private String auditorium3Name;

    @Value("${a1.numberOfSeats}")
    private long auditorium1NumberOfSeats;

    @Value("${a2.numberOfSeats}")
    private long auditorium2NumberOfSeats;

    @Value("${a3.numberOfSeats}")
    private long auditorium3NumberOfSeats;

    @Value("#{'${a1.vipSeats}'.split(',')}")
    private Set<Long> auditorium1VipSeats;

    @Value("#{'${a2.vipSeats}'.split(',')}")
    private Set<Long> auditorium2VipSeats;

    @Value("#{'${a3.vipSeats}'.split(',')}")
    private Set<Long> auditorium3VipSeats;

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean
    public UserDAO userDAO() {
        UserDAO dao = new UserDAO();
        dao.setJdbcTemplate(jdbcTemplate());
        dao.setTableName("t_user");
        return dao;
    }

    @Bean
    public TicketDAO ticketDAO() {
        TicketDAO dao = new TicketDAO();
        dao.setJdbcTemplate(jdbcTemplate());
        dao.setTableName("t_ticket");
        dao.setEventDAO(eventDAO());
        dao.setUserDAO(userDAO());
        return dao;
    }

    @Bean
    public EventDAO eventDAO() {
        EventDAO dao = new EventDAO();
        dao.setJdbcTemplate(jdbcTemplate());
        dao.setTableName("t_event");
        dao.setAirdatesTableName("t_airdate");
        dao.setAuditoriumsTableName("t_auditorium");
        dao.setAuditoriumService(auditoriumServiceImpl());
        return dao;
    }

    @Bean
    public EventCounterDAO eventCounterDAO() {
        EventCounterDAO eventCounterDAO = new EventCounterDAO();
        eventCounterDAO.setJdbcTemplate(jdbcTemplate());
        eventCounterDAO.setTableName("t_event_counter");
        return eventCounterDAO;
    }

    @Bean
    public DiscountCounterDAO discountCounterDAO() {
        DiscountCounterDAO discountCounterDAO = new DiscountCounterDAO();
        discountCounterDAO.setJdbcTemplate(jdbcTemplate());
        discountCounterDAO.setTableName("t_discount_counter");
        return discountCounterDAO;
    }

    @Bean
    public UserAccountDAO userAccountDAO() {
        UserAccountDAO userAccountDAO = new UserAccountDAO();
        userAccountDAO.setJdbcTemplate(jdbcTemplate());
        userAccountDAO.setTableName("t_user_account");
        return userAccountDAO;
    }

    @Bean
    public UserService userServiceImpl() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDAO(userDAO());
        return userService;
    }

    @Bean
    public BookingService bookingServiceImpl() {
        BookingServiceImpl bookingService = new BookingServiceImpl();
        bookingService.setTicketDAO(ticketDAO());
        bookingService.setDiscountService(discountService);
        return bookingService;
    }

    @Bean
    public EventService eventServiceImpl() {
        EventServiceImpl eventService = new EventServiceImpl();
        eventService.setEventDAO(eventDAO());
        return eventService;
    }

    @Bean
    public AuditoriumService auditoriumServiceImpl() {
        HashSet<Auditorium> auditoriums = new HashSet<>();

        Auditorium auditorium1 = new Auditorium();
        auditorium1.setName(auditorium1Name);
        auditorium1.setNumberOfSeats(auditorium1NumberOfSeats);
        auditorium1.setVipSeats(auditorium1VipSeats);
        auditoriums.add(auditorium1);

        Auditorium auditorium2 = new Auditorium();
        auditorium2.setName(auditorium2Name);
        auditorium2.setNumberOfSeats(auditorium2NumberOfSeats);
        auditorium2.setVipSeats(auditorium2VipSeats);
        auditoriums.add(auditorium2);

        Auditorium auditorium3 = new Auditorium();
        auditorium3.setName(auditorium3Name);
        auditorium3.setNumberOfSeats(auditorium3NumberOfSeats);
        auditorium3.setVipSeats(auditorium3VipSeats);
        auditoriums.add(auditorium3);

        return new AuditoriumServiceImpl(auditoriums);
    }

    @Bean
    public UserAccountService userAccountService() {
        UserAccountServiceImpl service = new UserAccountServiceImpl();
        service.setAccountDAO(userAccountDAO());
        return service;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JdbcOperations jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(initMethod = "init")
    @Scope("singleton")
    public DBCreator dbCreator() {
        DBCreator creator = new DBCreator();
        creator.setJdbcTemplate(jdbcTemplate());
        return creator;
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
