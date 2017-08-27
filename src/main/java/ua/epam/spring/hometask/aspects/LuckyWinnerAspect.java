package ua.epam.spring.hometask.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import ua.epam.spring.hometask.domain.Ticket;

import java.util.Set;

@Aspect
public class LuckyWinnerAspect {

    private LuckChecker luckChecker;

    @Before("execution(* ua.epam.spring.hometask.service.impl.BookingServiceImpl.bookTicket(..)) && args(ticket)")
    private void afterBookingServiceBookTicket(Ticket ticket) {
        checkLucky(ticket);
    }

    @Before("execution(* ua.epam.spring.hometask.service.impl.BookingServiceImpl.bookTickets(..)) && args(tickets)")
    private void afterBookingServiceBookTickets(Set<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            checkLucky(ticket);
        }
    }

    private void checkLucky(Ticket ticket) {
        if (luckChecker.checkLucky(ticket)) {
            ticket.getEvent().setBasePrice(0);
            if (ticket.getUser() != null) {
                ticket.getUser().addSystemMessage("Lucky event: zero price on ticket: " + ticket + " !");
            }
        }
    }

    public void setLuckChecker(LuckChecker luckChecker) {
        this.luckChecker = luckChecker;
    }
}
