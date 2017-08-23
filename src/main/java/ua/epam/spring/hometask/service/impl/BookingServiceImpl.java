package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.service.BookingService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.NavigableMap;
import java.util.Set;

public class BookingServiceImpl implements BookingService {
    private TicketDAO ticketDAO;

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        Auditorium auditorium = getAuditorium(event, dateTime);

        double basePrice = event.getBasePrice();
        double totalPrice = 0;
        double ticketPrice = 0;

        for (Long seatNumber : seats) {
            ticketPrice = getTicketPrice(auditorium, basePrice, seatNumber);

            if (EventRating.HIGH.equals(event.getRating())) {
                ticketPrice *= 1.2;
            }

            totalPrice += ticketPrice;
        }

        DiscountServiceImpl discountService = new DiscountServiceImpl();

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
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        ticketDAO.addAll(tickets);
    }

    @Override
    public boolean bookTicket(@Nonnull Ticket ticket) {
        return ticketDAO.save(ticket);
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return ticketDAO.getPurchasedTicketsForEvent(event, dateTime);
    }

    public void setTicketDAO(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }
}
