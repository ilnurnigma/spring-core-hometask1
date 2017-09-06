package ua.epam.spring.hometask.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.service.EventService;

import java.time.LocalDate;
import java.util.Set;

@Controller
public class EventServiceController {
    @Autowired
    EventService eventService;

    @RequestMapping("/eventServiceForm")
    public String saveUserForm() {
        return "eventServiceForm";
    }

    @RequestMapping(path = "/saveEvent", method = RequestMethod.POST)
    public ModelAndView saveUser(@RequestParam("name") String name,
                                 @RequestParam("basePrice") double basePrice,
                                 @RequestParam("rating") String rating) {

        Event event = new Event(name, basePrice, EventRating.valueOf(rating));
        eventService.save(event);

        ModelAndView mav = new ModelAndView("result");
        mav.addObject("msg", "Event " + event.getName() + " was saved.");
        return mav;
    }

    @RequestMapping(path = "/getEventByName", method = RequestMethod.POST)
    public ModelAndView getEventByName(@RequestParam("name") String name) {
        Event event = eventService.getByName(name);

        String msg;
        if (event != null) {
            msg = "Found event " + event.getName() + " " + event.getRating() + ".";
        } else {
            msg = "Did not find event with a name: " + name;
        }

        ModelAndView mav = new ModelAndView("result");
        mav.addObject("msg", msg);
        return mav;
    }

    @RequestMapping(path = "/getEventForDate", method = RequestMethod.POST)
    public ModelAndView getEventForDate(@RequestParam("from") String from,
                                        @RequestParam("to") String to) {
        Set<Event> events = eventService.getForDateRange(LocalDate.parse(from), LocalDate.parse(to));

        ModelAndView mav = new ModelAndView("events");
        mav.addObject("events", events);
        return mav;
    }

    @RequestMapping(path = "/getNextEvents", method = RequestMethod.POST)
    public ModelAndView getNextEvents(@RequestParam("to") String to) {
        Set<Event> events = eventService.getNextEvents(LocalDate.parse(to).atStartOfDay());

        ModelAndView mav = new ModelAndView("events");
        mav.addObject("events", events);
        return mav;
    }

    @RequestMapping(path = "/deleteEventByName", method = RequestMethod.POST)
    public ModelAndView deleteEventByName(@RequestParam("name") String name) {
        Event event = eventService.getByName(name);

        String msg;
        if (event != null) {
            msg = "Found and deleted event " + event.getName() + " " + event.getRating() + ".";
            eventService.remove(event);
        } else {
            msg = "Did not find event with a name: " + event;
        }

        ModelAndView mav = new ModelAndView("result");
        mav.addObject("msg", msg);
        return mav;
    }

}
