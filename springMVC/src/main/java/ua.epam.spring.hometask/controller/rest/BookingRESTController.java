package ua.epam.spring.hometask.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.UserAccount;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserAccountService;
import ua.epam.spring.hometask.service.UserService;

import java.util.Collection;

@Controller
public class BookingRESTController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;


    @RequestMapping(value = "/ticket/book", method = RequestMethod.POST)
    @ResponseBody
    public Ticket bookTicket(@RequestBody Ticket ticket) {
        bookingService.bookTicket(ticket);
        return ticket;
    }

    @RequestMapping(value = "/ticket/booked/all", method = {RequestMethod.GET, RequestMethod.POST},
            headers = "Accept=application/json,application/pdf")
    @ResponseBody
    public Collection<Ticket> getBookedTickets() {
        return bookingService.getBookedTickets();
    }

    @RequestMapping(value = "/ticket/{event}/price", method = RequestMethod.GET)
    @ResponseBody
    public double getTicketsPrice(@PathVariable("event") String event) {
        return bookingService.getTicketsPrice(eventService.getByName(event));
    }

    @RequestMapping(value = "/account/{email}/refill/{amount}", method = RequestMethod.PUT)
    @ResponseBody
    public UserAccount refillAccount(@PathVariable("email") String email, @PathVariable("amount") double amount) {
        return userAccountService.refill(userAccountService.getUserAccount(userService.getUserByEmail(email)), amount);
    }
}
