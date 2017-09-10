package ua.epam.spring.hometask.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

import java.util.HashSet;

@Controller
public class BookingServiceController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

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
/*        Event event = eventService.getByName(eventName);

        if (event == null) {
            ModelAndView result = new ModelAndView("result");
            result.addObject("msg", "Could not find event " + eventName);
            return result;
        }

        Ticket ticket = new Ticket(user, event, LocalDateTime.parse(dateTime), seat);
        bookingService.bookTicket(ticket);*/

        ModelAndView result = new ModelAndView("result");
        result.addObject("msg", "Booked ticket for " + eventName);
        return result;
    }

    @RequestMapping("/getTicketsPrice")
    public ModelAndView getTicketsPrice(@RequestParam("eventName") String eventName,
                                        @RequestParam("dateTime") String dateTime,
                                        @RequestParam("userEmail") String userEmail,
                                        @RequestParam("seats") String seats) {

/*        Event event = eventService.getByName(eventName);
        User user = userService.getUserByEmail(userEmail);


        double ticketsPrice = bookingService.getTicketsPrice(event, LocalDateTime.parse(dateTime),
                user, getSeats(seats));*/

        double ticketsPrice = 250;

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
        ModelAndView mav = new ModelAndView("result");
        mav.addObject("msg", "Booked tickets for event...");
        return mav;
    }
}
