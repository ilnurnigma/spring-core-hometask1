package ua.epam.spring.hometask.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.EventService;

@Controller
@RequestMapping("/event")
public class EventServiceRESTController {
    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Event addEvent(@RequestBody Event event) {
        return eventService.save(event);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Event getEvent(@PathVariable("name") String name) {
        return eventService.getByName(name);
    }

    @RequestMapping(value = "/delete/{name}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEvent(@PathVariable("name") String name) {
        eventService.remove(eventService.getByName(name));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity updateEvent(@RequestBody Event event) {
        eventService.remove(eventService.getByName(event.getName()));
        eventService.save(event);
        return new ResponseEntity(HttpStatus.OK);
    }
}
