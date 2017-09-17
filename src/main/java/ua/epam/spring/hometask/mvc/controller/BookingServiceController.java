package ua.epam.spring.hometask.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.UserAccount;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserAccountService;
import ua.epam.spring.hometask.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

@Controller
public class BookingServiceController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserAccountService userAccountService;

    @RequestMapping("/bookingServiceForm")
    public String bookingServiceForm() {
        return "bookingServiceForm";
    }

    @RequestMapping("/bookTicket")
    public ModelAndView bookTicket(@RequestParam("userEmail") String userEmail,
                                   @RequestParam("eventName") String eventName,
                                   @RequestParam("dateTime") String dateTime,
                                   @RequestParam("seat") long seat) {

        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            ModelAndView result = new ModelAndView("result");
            result.addObject("msg", "Could not find user by email " + userEmail);
            return result;
        }

        Event event = eventService.getByName(eventName);
        if (event == null) {
            ModelAndView result = new ModelAndView("result");
            result.addObject("msg", "Could not find event " + eventName);
            return result;
        }

        Ticket ticket = new Ticket(user, event, LocalDateTime.parse(dateTime), seat);
        boolean isBooked = bookingService.bookTicket(ticket);
        if (!isBooked) {
            ModelAndView result = new ModelAndView("result");
            result.addObject("msg", "Booking failed for event " + eventName
                    + ". Check that there is enough money on user's account.");
            return result;
        }

        ModelAndView result = new ModelAndView("result");
        result.addObject("msg", "Booked ticket for " + eventName);
        return result;
    }

    @RequestMapping("/getTicketsPrice")
    public ModelAndView getTicketsPrice(@RequestParam("eventName") String eventName) {
        Event event = eventService.getByName(eventName);
        if (event == null) {
            ModelAndView mav = new ModelAndView("result");
            mav.addObject("msg", "Event " + eventName + " was not found.");
            return mav;
        }

        double ticketsPrice = bookingService.getTicketsPrice(event);

        ModelAndView mav = new ModelAndView("result");
        mav.addObject("msg", "Tickets price is " + ticketsPrice);

        return mav;
    }

    private HashSet<Long> getSeats(String seatsAsString) {
        String[] seatsArray = seatsAsString.split(",");
        HashSet<Long> seatNumbers = new HashSet<>();
        for (String seat : seatsArray) {
            seatNumbers.add(Long.getLong(seat));
        }

        return seatNumbers;
    }

    @RequestMapping("/getBookedTickets")
    public ModelAndView getBookedTickets() {
        Collection<Ticket> tickets = bookingService.getBookedTickets();

        if (tickets.isEmpty()) {
            ModelAndView mav = new ModelAndView("result");
            mav.addObject("msg", "No tickets are booked.");
            return mav;
        }

        ModelAndView mav = new ModelAndView("tickets");
        mav.addObject("tickets", tickets);
        return mav;
    }

    @RequestMapping("/addAmount")
    public ModelAndView addAmount(@RequestParam("email") String email, @RequestParam("amount") double amount) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            ModelAndView result = new ModelAndView("result");
            result.addObject("msg", "Could not find user by email " + email);
            return result;
        }

        UserAccount account = userAccountService.getUserAccount(user);
        account = userAccountService.refill(account, amount);

        ModelAndView mav = new ModelAndView("result");
        mav.addObject("msg", "Money added. Total amount: " + account.getAmount());
        return mav;
    }
}
