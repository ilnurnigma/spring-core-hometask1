package ua.epam.spring.hometask.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.Ticket;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Created on 8/21/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingServiceImplTest {

    @Autowired
    private ApplicationContext ctx;

    private Event event;
    private LocalDateTime dateTime;
    private BookingServiceImpl service;

    @Before
    public void setUp() throws Exception {
        service = ctx.getBean("bookingServiceImpl", BookingServiceImpl.class);

        event = new Event();
        event.setBasePrice(100);

        TreeMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();
        dateTime = LocalDateTime.now();
        Auditorium auditorium = new Auditorium();
        HashSet<Long> vipSeats = new HashSet<>();
        vipSeats.add(5L);
        auditorium.setVipSeats(vipSeats);
        auditoriums.put(dateTime, auditorium);
        event.setAuditoriums(auditoriums);
    }

    @Test
    public void givenApplicationContextReturnBeanImplementation() {
        assertTrue(ctx.getBean("bookingServiceImpl") instanceof BookingServiceImpl);
    }

    @Test
    public void givenBasePriceReturnBasePrice() throws Exception {
        HashSet<Long> seats = new HashSet<>();
        seats.add(1L);

        double actualPrice = service.getTicketsPrice(event, LocalDateTime.now(), null, seats);

        assertEquals(100, actualPrice, 0);
    }

    @Test
    public void givenBasePriceReturnBasePriceForTwoTickets() throws Exception {
        HashSet<Long> seats = new HashSet<>();
        seats.add(1L);
        seats.add(2L);

        double actualPrice = service.getTicketsPrice(event, LocalDateTime.now(), null, seats);

        assertEquals(200, actualPrice, 0);
    }

    @Test
    public void givenBasePriceReturnVipPrice() throws Exception {
        HashSet<Long> seats = new HashSet<>();
        seats.add(5L);

        double actualPrice = service.getTicketsPrice(event, dateTime, null, seats);

        assertEquals(200, actualPrice, 0);
    }

    @Test
    public void givenBasePriceAndHighRatedMovieReturnHighRatedPrice() throws Exception {
        event.setRating(EventRating.HIGH);
        HashSet<Long> seats = new HashSet<>();
        seats.add(1L);

        double actualPrice = service.getTicketsPrice(event, dateTime, null, seats);

        assertEquals(120, actualPrice, 0);
    }

    @Test
    public void bookTickets() throws Exception {
        HashSet<Ticket> tickets = new HashSet<>();
        tickets.add(new Ticket(null, event, dateTime, 1L));
        service.bookTickets(tickets);
    }

    @Test
    public void getPurchasedTicketsForEvent() throws Exception {
        HashSet<Ticket> tickets = new HashSet<>();
        Ticket ticket = new Ticket(null, event, dateTime, 1L);
        tickets.add(ticket);

        service.bookTickets(tickets);

        Set<Ticket> purchasedTickets = service.getPurchasedTicketsForEvent(event, dateTime);

        assertTrue(purchasedTickets.contains(ticket));
    }

}