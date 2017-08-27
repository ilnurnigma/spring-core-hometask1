package ua.epam.spring.hometask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import ua.epam.spring.hometask.AdminCommand;
import ua.epam.spring.hometask.UserCommand;
import ua.epam.spring.hometask.aspects.CounterAspect;
import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.dao.UserDAO;
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
        return new CounterAspect();
    }

    @Bean
    @Scope("prototype")
    public Event event() {
        return new Event();
    }
}
