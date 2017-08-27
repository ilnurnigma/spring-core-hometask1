package ua.epam.spring.hometask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Aspect
public class CounterAspect {
    private Map<Event, EventCounter> counters = new HashMap<>();

    @AfterReturning(pointcut = "execution(* ua.epam.spring.hometask.dao.EventDAO.getByName(..))",
            returning = "event")
    private void afterEventDAOGetByName(Event event) {
        EventCounter counter = counters.get(event);
        if (counter == null) {
            counter = new EventCounter();
        }

        counter.addNameAccessCounter();
        counters.put(event, counter);
    }

    @After("execution(* ua.epam.spring.hometask.domain.Event.getBasePrice())")
    private void afterEventGetBasePrice(JoinPoint joinPoint) {
        Event event = (Event) joinPoint.getTarget();

        EventCounter counter = counters.get(event);
        if (counter == null) {
            counter = new EventCounter();
        }

        counter.addPriceAccessCounter();
        counters.put(event, counter);
    }

    @After("execution(* ua.epam.spring.hometask.service.impl.BookingServiceImpl.bookTickets(..)) && args(tickets)")
    private void afterBookingServiceBookTickets(Set<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            addBookTicketCounter(ticket);
        }
    }

    @After("execution(* ua.epam.spring.hometask.service.impl.BookingServiceImpl.bookTicket(..)) && args(ticket)")
    private void afterBookingServiceBookTicket(Ticket ticket) {
        addBookTicketCounter(ticket);
    }

    private void addBookTicketCounter(Ticket ticket) {
        Event event = ticket.getEvent();
        EventCounter counter = counters.get(event);
        if (counter == null) {
            counter = new EventCounter();
        }

        counter.addBookTicketCounter();
        counters.put(event, counter);
    }

    public long getNameAccessCounter(Event event) {
        return counters.get(event).getNameAccessCounter();
    }

    public long getPriceAccessCounter(Event event) {
        return counters.get(event).getPriceAccesCounter();
    }

    public long getBookTicketCounter(Event event) {
        return counters.get(event).getBookTicketCounter();
    }
}
