package ua.epam.spring.hometask.service.impl;

import org.springframework.transaction.annotation.Transactional;
import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserAccountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.Set;

public class BookingServiceImpl implements BookingService {
    private TicketDAO ticketDAO;
    private DiscountService discountService;
    private UserAccountService userAccountService;
    private EventService eventService;

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        return getTicketsPriceWithDiscounts(event, dateTime, user, seats);
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event) {
        return event.getBasePrice();
    }

    private double getTicketsPriceWithDiscounts(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        Auditorium auditorium = getAuditorium(event, dateTime);

        double basePrice = event.getBasePrice();
        double totalPrice = 0;
        double ticketPrice;

        for (Long seatNumber : seats) {
            ticketPrice = getTicketPrice(auditorium, basePrice, seatNumber);

            if (EventRating.HIGH.equals(event.getRating())) {
                ticketPrice *= 1.2;
            }

            totalPrice += ticketPrice;
        }

        return totalPrice - totalPrice * discountService.getDiscount(user, event, dateTime, seats.size()) / 100;
    }

    private double getTicketPrice(Auditorium auditorium, double basePrice, Long seatNumber) {
        if (isVipSeat(auditorium, seatNumber)) {
            return basePrice * 2;
        }

        return basePrice;
    }

    private boolean isVipSeat(Auditorium auditorium, Long seatNumber) {
        Set<Long> vipSeats = auditorium.getVipSeats();
        return vipSeats.contains(seatNumber);
    }

    private Auditorium getAuditorium(Event event, LocalDateTime dateTime) {
        NavigableMap<LocalDateTime, Auditorium> auditoriums = event.getAuditoriums();
        return auditoriums.get(dateTime);
    }

    @Override
    @Transactional
    public boolean bookTicket(@Nonnull Ticket ticket) {
        double basePrice = getBasePrice(ticket);

        User user = ticket.getUser();
        double amount = userAccountService.getAmount(user);
        if (amount < basePrice) {
            return false;
        }

        userAccountService.subtractAmount(user, basePrice);
        if (true) {
            throw new RuntimeException("My Exception");
        }
        ticketDAO.save(ticket);
        return true;
    }

    private double getBasePrice(@Nonnull Ticket ticket) {
        double basePrice = ticket.getEvent().getBasePrice();

        if (basePrice <= 0) {
            basePrice = eventService.getByName(ticket.getEvent().getName()).getBasePrice();
        }

        if (basePrice <= 0) {
            String msg = "Base price for the event " + ticket.getEvent().getName() + " can not be less or equal to zero.";
            throw new IllegalArgumentException(msg);
        }
        return basePrice;
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return ticketDAO.getPurchasedTicketsForEvent(event, dateTime);
    }

    @Override
    public Collection<Ticket> getBookedTickets() {
        return ticketDAO.getAll();
    }

    public void setTicketDAO(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
