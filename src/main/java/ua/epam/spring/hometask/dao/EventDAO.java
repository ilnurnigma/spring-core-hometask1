package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 8/23/2017.
 */
public class EventDAO extends DomainObjectDAO<Event> {
    public @Nonnull
    Set<Event> getForDateRange(@Nonnull LocalDate from,
                               @Nonnull LocalDate to) {
        Set<Event> result = new HashSet<>();
        for (Event event : domainObjects) {
            if (event.airsOnDates(from, to)) {
                result.add(event);
            }
        }

        return result;
    }

    public @Nonnull
    Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        return getForDateRange(LocalDate.now(), to.toLocalDate());
    }

    public Event getByName(String name) {
        for (Event event : domainObjects) {
            if (event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }
}
