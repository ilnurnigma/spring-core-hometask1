package ua.epam.spring.hometask;

import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.service.EventService;

import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created on 8/23/2017.
 */
public class AdminCommand {
    private EventService eventService;

    public AdminCommand(EventService eventService) {
        this.eventService = eventService;
    }

    public Event enterEvent(String name, EventRating rating, double price) {
        Event event = new Event();
        event.setName(name);
        event.setRating(rating);
        event.setBasePrice(price);

        TreeMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();
        Auditorium auditorium = new Auditorium();
        auditoriums.put(LocalDateTime.of(2017, 8, 23, 20, 0), auditorium);
        event.setAuditoriums(auditoriums);
        TreeSet<LocalDateTime> airDates = new TreeSet<>();
        airDates.add(LocalDateTime.of(2017, 8, 23, 20, 0));
        event.setAirDates(airDates);
        return eventService.save(event);
    }

    public void viewPurchasedTickets() {

    }
}
