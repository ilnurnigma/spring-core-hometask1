package ua.epam.spring.hometask.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Controller
public class PdfController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/getAllUsers", headers = "Accept=application/pdf")
    public ModelAndView getAllUsers() {
        Collection<User> users = userService.getAll();

        if (users.isEmpty()) {
            ModelAndView view = new ModelAndView("result");
            view.addObject("msg", "There are no users in the DB.");
            return view;
        }

        ArrayList<String> messages = new ArrayList<>();
        for (User user : users) {
            messages.add(user.getFirstName() + " " + user.getLastName() + " " + user.getEmail());
        }

        ModelAndView mav = new ModelAndView("pdf");
        mav.addObject("messages", messages);
        return mav;
    }

    @RequestMapping(value = "/getAllEvents", headers = "Accept=application/pdf")
    public ModelAndView getAllEvents() {
        Collection<Event> events = eventService.getAll();


        if (events.isEmpty()) {
            ModelAndView view = new ModelAndView("result");
            view.addObject("msg", "There are no events in the DB.");
            return view;
        }

        ArrayList<String> messages = new ArrayList<>();
        for (Event event : events) {
            messages.add(event.getName() + " " + event.getBasePrice() + " " + event.getRating());
        }

        ModelAndView mav = new ModelAndView("pdf");
        mav.addObject("messages", messages);
        return mav;
    }

}
