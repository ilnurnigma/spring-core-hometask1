package ua.epam.spring.hometask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import ua.epam.spring.hometask.dao.EventCounterDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;

import java.util.Set;

@Aspect
public class CounterAspect {
    private EventCounterDAO eventCounterDAO;

    @AfterReturning(pointcut = "execution(* ua.epam.spring.hometask.dao.EventDAO.getByName(..))",
            returning = "event")
    private void afterEventDAOGetByName(Event event) {
        if (event != null) {
            eventCounterDAO.addNameAccessCounter(event);
        }
    }

    @After("execution(* ua.epam.spring.hometask.domain.Event.getBasePrice())")
    private void afterEventGetBasePrice(JoinPoint joinPoint) {
        Event event = (Event) joinPoint.getTarget();
        eventCounterDAO.addPriceAccesCounter(event);
    }

    @After("execution(* ua.epam.spring.hometask.service.impl.BookingServiceImpl.bookTicket(..)) && args(ticket)")
    private void afterBookingServiceBookTicket(Ticket ticket) {
        addBookTicketCounter(ticket);
    }

    private void addBookTicketCounter(Ticket ticket) {
        Event event = ticket.getEvent();
        eventCounterDAO.addBookTicketCounter(event);
    }

    public long getNameAccessCounter(Event event) {
        return eventCounterDAO.getNameAccessCounter(event);
    }

    public long getPriceAccessCounter(Event event) {
        return eventCounterDAO.getPriceAccesCounter(event);
    }

    public long getBookTicketCounter(Event event) {
        return eventCounterDAO.getBookTicketCounter(event);
    }

    public void setEventCounterDAO(EventCounterDAO eventCounterDAO) {
        this.eventCounterDAO = eventCounterDAO;
    }
}
