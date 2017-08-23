package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.domain.DomainObject;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.EventService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class EventServiceImpl extends DomainObjectServiceImpl<Event> implements EventService {
    private EventDAO eventDAO;

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        for (DomainObject event : domainObjects) {
            if (name.equals(((Event) event).getName())) {
                return (Event) event;
            }
        }

        return null;
    }

    @Nonnull
    @Override
    public Set<Event> getForDateRange(@Nonnull LocalDate from, @Nonnull LocalDate to) {
        return eventDAO.getForDateRange(from, to);
    }

    @Nonnull
    @Override
    public Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        return eventDAO.getNextEvents(to);
    }
}
