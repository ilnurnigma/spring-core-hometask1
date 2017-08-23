package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created on 8/23/2017.
 */
public class TicketDAO extends DomainObjectDAO<Ticket>{
    public boolean addAll(Set<Ticket> tickets) {
        return this.domainObjects.addAll(tickets);
    }

    /**
     * Getting all purchased domainObjects for event on specific air date and time
     *
     * @param event    Event to get domainObjects for
     * @param dateTime Date and time of airing of event
     * @return set of all purchased domainObjects
     */
    public @Nonnull
    Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        Set<Ticket> result = new HashSet<>();
        for (Ticket ticket : domainObjects) {
            if (ticket.getEvent().equals(event) && ticket.getDateTime().equals(dateTime)) {
                result.add(ticket);
            }
        }

        return result;
    }
}
