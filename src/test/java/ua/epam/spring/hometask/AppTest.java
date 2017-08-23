package ua.epam.spring.hometask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.EventService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

/**
 * Created on 8/23/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class AppTest {
    @Autowired
    private ApplicationContext ctx;

    private UserCommand userCommand;

    @Before
    public void setUp() {
        userCommand = ctx.getBean("userCommand", UserCommand.class);
    }

    @Test
    public void enterEvent() {
        EventService eventService = ctx.getBean("eventServiceImpl", EventService.class);
        Event event = new Event();
        event.setName("Game of Thrones 7");
        event.setRating(EventRating.HIGH);
        event.setBasePrice(100);
        TreeMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();
        Auditorium auditorium = new Auditorium();
        auditoriums.put(LocalDateTime.of(2017, 8, 23, 20, 0), auditorium);
        event.setAuditoriums(auditoriums);
        TreeSet<LocalDateTime> airDates = new TreeSet<>();
        airDates.add(LocalDateTime.of(2017, 8, 23, 20, 0));
        event.setAirDates(airDates);
        eventService.save(event);

        Event event1 = eventService.getByName("Game of Thrones 7");

        assertEquals(event, event1);

        User user = userCommand.register("John", "Snow", "john_snow@epam.com");
        Set<Event> events = userCommand.viewEvents(LocalDate.now(), LocalDate.now().plusDays(10));
        userCommand.getNextEvents(LocalDateTime.now().plusDays(10));

        Event next = events.iterator().next();
        HashSet<Long> seats = new HashSet<>();
        seats.add(1L);
        LocalDateTime dateTime = next.getAirDates().pollFirst();
        double ticketsPrice = userCommand.getTicketsPrice(next, dateTime, user, seats);

        HashSet<Ticket> tickets = new HashSet<>();
        tickets.add(new Ticket(user, event, dateTime, 1L));
        userCommand.buyTickets(tickets);
    }

}