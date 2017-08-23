package ua.epam.spring.hometask;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created on 8/23/2017.
 */
public class UserCommand {
    private UserService userService;
    private EventService eventService;
    private BookingService bookingService;

    public UserCommand(UserService userService, EventService eventService, BookingService bookingService) {
        this.userService = userService;
        this.eventService = eventService;
        this.bookingService = bookingService;
    }

    public User register(String firstName, String lastName, String email) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        return userService.save(user);
    }

    public Set<Event> viewEvents(@Nonnull LocalDate from, @Nonnull LocalDate to) {
        return eventService.getForDateRange(from, to);
    }

    @Nonnull
    public Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        return eventService.getNextEvents(to);
    }

    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user,
                                  @Nonnull Set<Long> seats) {
        return bookingService.getTicketsPrice(event, dateTime, user, seats);
    }

    public void buyTickets(@Nonnull Set<Ticket> tickets) {
        bookingService.bookTickets(tickets);
    }

    public Ticket buyTicket(User user, Event event, LocalDateTime dateTime, long seat) {
        Ticket ticket = new Ticket(user, event, dateTime, seat);
        if (bookingService.bookTicket(ticket)) {
            return ticket;
        }

        return null;
    }
}
