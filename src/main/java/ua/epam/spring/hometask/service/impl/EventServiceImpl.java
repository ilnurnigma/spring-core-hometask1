package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.domain.DomainObject;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.EventService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EventServiceImpl extends DomainObjectServiceImpl<Event> implements EventService {

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
}
