package ua.epam.spring.hometask;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created on 8/23/2017.
 */
public class AdminCommand {
    private EventService eventService;
    private BookingService bookingService;
    private AuditoriumService auditoriumService;

    public AdminCommand(EventService eventService, BookingService bookingService, AuditoriumService auditoriumService) {
        this.eventService = eventService;
        this.bookingService = bookingService;
        this.auditoriumService = auditoriumService;
    }

    public Event enterEvent(String name, EventRating rating, double price) {
        Event event = new Event();
        event.setName(name);
        event.setRating(rating);
        event.setBasePrice(price);
        return eventService.save(event);
    }

    public boolean addAirDateTime(Event event, LocalDateTime dateTime, String auditoriumName) {
        event.addAirDateTime(dateTime, auditoriumService.getByName(auditoriumName));
        eventService.save(event);
        return true;
    }

    public Set<Ticket> viewPurchasedTickets(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return bookingService.getPurchasedTicketsForEvent(event, dateTime);
    }
}
