package ua.epam.spring.hometask;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created on 8/23/2017.
 */
public class UserCommand {
    private UserService userService;
    private EventService eventService;

    public UserCommand(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
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

    public @Nonnull
    Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        return eventService.getNextEvents(to);
    }
}
