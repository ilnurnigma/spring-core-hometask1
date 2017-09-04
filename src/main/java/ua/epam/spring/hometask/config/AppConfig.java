package ua.epam.spring.hometask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.epam.spring.hometask.AdminCommand;
import ua.epam.spring.hometask.UserCommand;
import ua.epam.spring.hometask.aspects.*;
import ua.epam.spring.hometask.dao.EventCounterDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

@Configuration
@Import(ServiceBeansConfig.class)
@EnableAspectJAutoProxy
public class AppConfig {
    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private AuditoriumService auditoriumService;

    @Autowired
    private EventCounterDAO eventCounterDAO;


    @Bean
    public UserCommand userCommand() {
        return new UserCommand(userService, eventService, bookingService);
    }

    @Bean
    public AdminCommand adminCommand() {
        return new AdminCommand(eventService, bookingService, auditoriumService);
    }

    @Bean
    public CounterAspect counterAspect() {
        CounterAspect aspect = new CounterAspect();
        aspect.setEventCounterDAO(eventCounterDAO);
        return aspect;
    }

    @Bean
    public DiscountAspect discountAspect() {
        return new DiscountAspect();
    }

    @Bean
    public LuckyWinnerAspect luckyWinnerAspect() {
        LuckyWinnerAspect luckyWinnerAspect = new LuckyWinnerAspect();
        luckyWinnerAspect.setLuckChecker(luckChecker());
        return luckyWinnerAspect;
    }

    @Bean
    public LuckChecker luckChecker() {
        return new DefaultLuckChecker();
    }

    @Bean
    @Scope("prototype")
    public Event event() {
        return new Event();
    }


}
