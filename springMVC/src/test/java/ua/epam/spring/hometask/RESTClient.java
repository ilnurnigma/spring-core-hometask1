package ua.epam.spring.hometask;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ua.epam.spring.hometask.domain.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

@Ignore("For localhost testing")
public class RESTClient {

    public static final String PAGE_URI = "http://localhost:9090";

    @Test
    public void ticketBookingOperations() throws IOException {
        RestTemplate template = new RestTemplate();

        double basePrice = 100;
        String eventName = "event1";
        Event event;
        event = template.getForObject(PAGE_URI + "/event/{name}", Event.class, eventName);
        if (event == null) {
            event = new Event(eventName, basePrice, EventRating.HIGH);
            event = template.postForObject(PAGE_URI + "/event/add", event, Event.class);
        }
        Assert.assertEquals(eventName, event.getName());

        User user = new User("John", "Snow", "john_snow@mail.com");
        user = template.postForObject(PAGE_URI + "/user/add", user, User.class);

        Double price = template.getForObject(PAGE_URI + "/ticket/{event}/price", Double.class, event.getName());
        Assert.assertEquals(basePrice, price, 0);

        template.put(PAGE_URI + "/account/{email}/refill/{amount}", UserAccount.class, user.getEmail(), price);

        Ticket ticket = new Ticket(user, event, LocalDateTime.now(), 1);
        ticket = template.postForObject(PAGE_URI + "/ticket/book", ticket, Ticket.class);

        Ticket[] tickets = template.getForObject(PAGE_URI + "/ticket/booked/all", Ticket[].class);
        Assert.assertTrue(Arrays.asList(tickets).contains(ticket));
    }

    @Test
    public void contentNegotiation() throws IOException {
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_PDF));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        byte[] pdf = template.postForObject(PAGE_URI + "/ticket/booked/all", entity, byte[].class);
        Files.write(Paths.get("bookedTickets.pdf"), pdf);
    }

    @Test
    public void eventCRUD() {
        RestTemplate template = new RestTemplate();

        Event event = new Event("crudEvent", (double) 100, EventRating.HIGH);

        Event createdEvent = template.postForObject(PAGE_URI + "/event/add", event, Event.class);
        Assert.assertEquals(event, createdEvent);

        event.setBasePrice(200);
        event.setRating(EventRating.LOW);
        template.put(PAGE_URI + "/event/update", event);

        Event updatedEvent = template.getForObject(PAGE_URI + "/event/{name}", Event.class, event.getName());
        Assert.assertEquals(event, updatedEvent);
        System.out.println(updatedEvent);

        template.delete(PAGE_URI + "/event/delete/{name}", event.getName());

        Event deletedEvent = template.getForObject(PAGE_URI + "/event/{name}", Event.class, event.getName());
        Assert.assertNull(deletedEvent);
    }

    @Test
    public void userCRUD() {
        RestTemplate template = new RestTemplate();

        User user = new User("crudUserName", "crudUserSurname", "crudUser@mail.com");

        User createdUser = template.postForObject(PAGE_URI + "/user/add", user, User.class);
        Assert.assertEquals(user, createdUser);

        user.setFirstName("changedName");
        user.setLastName("changedSurname");
        template.put(PAGE_URI + "/user/update", user);

        User updatedUser = template.getForObject(PAGE_URI + "/user/{email}/", User.class, user.getEmail());
        Assert.assertEquals(user, updatedUser);
        System.out.println(updatedUser);

        template.delete(PAGE_URI + "/user/delete/{email}/", user.getEmail());

        User deletedUser = template.getForObject(PAGE_URI + "/user/{email}/", User.class, user.getEmail());
        Assert.assertNull(deletedUser);
    }
}
