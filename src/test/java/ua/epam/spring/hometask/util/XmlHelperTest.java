package ua.epam.spring.hometask.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.AuditoriumService;

import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created on 9/6/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = AppConfig.class)
public class XmlHelperTest {
    @Autowired
    private ApplicationContext ctx;

    private Jaxb2Marshaller marshaller;
    private XmlHelper xmlHelper;

    @Before
    public void setUp() throws Exception {
        marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(BatchUpload.class);
        marshaller.setMarshallerProperties(new HashMap<String, Object>() {{
            put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
        }});

        xmlHelper = new XmlHelper();
        xmlHelper.setMarshaller(marshaller);
    }

    @Test
    public void unmarshal() throws FileNotFoundException {
        Set<User> users = getUsers();
        Set<Event> events = getEvents();

        BatchUpload upload = new BatchUpload();
        upload.setUsers(users);
        upload.setEvents(events);

        FileOutputStream outputStream = new FileOutputStream("xml/batchUpload.xml");
        marshaller.marshal(upload, new StreamResult(outputStream));

        FileInputStream inputStream = new FileInputStream("xml/batchUpload.xml");
        xmlHelper.unmarshal(inputStream);

        assertEquals(users, xmlHelper.getUsers());
        assertEquals(events, xmlHelper.getEvents());
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
        AuditoriumService auditoriumService = ctx.getBean("auditoriumServiceImpl", AuditoriumService.class);
        event1.assignAuditorium(LocalDateTime.now().plusDays(2), auditoriumService.getByName("Red"));
        Event event2 = new Event("Black sails", 90, EventRating.MID);
        event2.assignAuditorium(LocalDateTime.now().plusDays(4), auditoriumService.getByName("Yellow"));

        Set<Event> events = new HashSet<>();
        events.add(event1);
        events.add(event2);

        return events;
    }

}