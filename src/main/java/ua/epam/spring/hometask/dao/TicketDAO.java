package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created on 8/23/2017.
 */
public class TicketDAO {
    private Collection<Ticket> tickets = new HashSet<>();

    public boolean addAll(Set<Ticket> tickets) {
        return this.tickets.addAll(tickets);
    }

    /**
     * Getting all purchased tickets for event on specific air date and time
     *
     * @param event    Event to get tickets for
     * @param dateTime Date and time of airing of event
     * @return set of all purchased tickets
     */
    public @Nonnull
    Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        Set<Ticket> result = new HashSet<>();
        for (Ticket ticket : tickets) {
            if (ticket.getEvent().equals(event) && ticket.getDateTime().equals(dateTime)) {
                result.add(ticket);
            }
        }

        return result;
    }
}
