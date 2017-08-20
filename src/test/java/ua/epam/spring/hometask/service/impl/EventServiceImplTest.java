package ua.epam.spring.hometask.service.impl;

import org.junit.Test;
import ua.epam.spring.hometask.domain.Event;

import static org.junit.Assert.*;

public class EventServiceImplTest {
    @Test
    public void givenEventReturnByName() throws Exception {
        EventServiceImpl eventService = new EventServiceImpl();
        Event givenEvent = new Event();
        givenEvent.setName("BattleEvent");
        eventService.save(givenEvent);

        Event returnedEvent = eventService.getByName("BattleEvent");

        assertEquals(givenEvent, returnedEvent);
    }

}