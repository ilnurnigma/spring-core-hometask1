package ua.epam.spring.hometask.util;

import org.junit.Before;
import org.junit.Test;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.User;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created on 9/6/2017.
 */
public class XmlHelperTest {
    private Jaxb2Marshaller marshaller;

    @Before
    public void setUp() throws Exception {
        marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(BatchUpload.class);
        marshaller.setMarshallerProperties(new HashMap<String, Object>() {{
            put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
        }});
    }

    @Test
    public void marshallUsers() throws FileNotFoundException {
        Set<User> users = getUsers();
        Set<Event> events = getEvents();

        BatchUpload upload = new BatchUpload();
        upload.setUsers(users);
        upload.setEvents(events);

        FileOutputStream outputStream = new FileOutputStream("xml/batchUpload.xml");
        marshaller.marshal(upload, new StreamResult(outputStream));

        FileInputStream inputStream = new FileInputStream("xml/batchUpload.xml");
        BatchUpload uploaded = (BatchUpload) marshaller.unmarshal(new StreamSource(inputStream));

        assertEquals(users, uploaded.getUsers());
        assertEquals(events, uploaded.getEvents());
    }


    private Set<User> getUsers() {
        User user1 = new User("John", "Snow", "john.snow@mail.com");
        User user2 = new User("Daenerys", "Targaryen", "daenerys_targaryen@mail.com");

        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);

        return users;
    }

    private Set<Event> getEvents() {
        Event event1 = new Event("Game of thrones", 100, EventRating.HIGH);
        Event event2 = new Event("Black sails", 90, EventRating.MID);

        Set<Event> events = new HashSet<>();
        events.add(event1);
        events.add(event2);

        return events;
    }

}