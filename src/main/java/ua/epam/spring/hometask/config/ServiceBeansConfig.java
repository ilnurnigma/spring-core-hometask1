package ua.epam.spring.hometask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.epam.spring.hometask.dao.EventCounterDAO;
import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.*;
import ua.epam.spring.hometask.service.impl.AuditoriumServiceImpl;
import ua.epam.spring.hometask.service.impl.BookingServiceImpl;
import ua.epam.spring.hometask.service.impl.EventServiceImpl;
import ua.epam.spring.hometask.service.impl.UserServiceImpl;

import java.util.HashSet;
import java.util.Set;

@Configuration
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
        return new UserDAO();
    }

    @Bean
    public TicketDAO ticketDAO() {
        return new TicketDAO();
    }

    @Bean
    public EventDAO eventDAO() {
        return new EventDAO();
    }

    @Bean
    public EventCounterDAO eventCounterDAO() {
        EventCounterDAO eventCounterDAO = new EventCounterDAO();
        eventCounterDAO.setJdbcTemplate(jdbcTemplate());
        return eventCounterDAO;
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
        auditorium1.setName(auditorium2Name);
        auditorium1.setNumberOfSeats(auditorium2NumberOfSeats);
        auditorium1.setVipSeats(auditorium2VipSeats);
        auditoriums.add(auditorium2);

        Auditorium auditorium3 = new Auditorium();
        auditorium1.setName(auditorium3Name);
        auditorium1.setNumberOfSeats(auditorium3NumberOfSeats);
        auditorium1.setVipSeats(auditorium3VipSeats);
        auditoriums.add(auditorium3);

        return new AuditoriumServiceImpl(auditoriums);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
