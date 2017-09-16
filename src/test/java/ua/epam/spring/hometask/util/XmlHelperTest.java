package ua.epam.spring.hometask.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public static final String REGISTERED_USER_BOOKING_MANAGER = "ROLE_REGISTERED_USER,ROLE_BOOKING_MANAGER";
    public static final String REGISTERED_USER = "ROLE_REGISTERED_USER";
    public static final String PASSWORD = new BCryptPasswordEncoder(11).encode("12345");

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
        user1.setRoles(REGISTERED_USER_BOOKING_MANAGER);
        user1.setPassword(PASSWORD);

        User user2 = new User("Daenerys", "Targaryen", "daenerys_targaryen@mail.com");
        user2.setRoles(REGISTERED_USER_BOOKING_MANAGER);
        user2.setPassword(PASSWORD);

        User user3 = new User("Tyrion", "Lannister", "tyrion_lannister@mail.com");
        user3.setRoles(REGISTERED_USER);
        user3.setPassword(PASSWORD);

        User user4 = new User("Arya", "Stark", "arya_stark@mail.com");
        user4.setRoles(REGISTERED_USER);
        user4.setPassword(PASSWORD);

        User user5 = new User("Sansa", "Stark", "sansa_stark@mail.com");
        user5.setRoles(REGISTERED_USER);
        user5.setPassword(PASSWORD);

        User user6 = new User("Cersei", "Lannister", "cersei_lannister@mail.com");
        user6.setRoles(REGISTERED_USER_BOOKING_MANAGER);
        user6.setPassword(PASSWORD);

        User user7 = new User("Jaime", "Lannister", "jaime_lannister@mail.com");
        user7.setRoles(REGISTERED_USER);
        user7.setPassword(PASSWORD);

        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);

        return users;
    }

    private Set<Event> getEvents() {
        Event event1 = new Event("Game of thrones", 100, EventRating.HIGH);
/*        AuditoriumService auditoriumService = ctx.getBean("auditoriumServiceImpl", AuditoriumService.class);
        LocalDateTime dateTime1 = LocalDateTime.now().plusDays(2);
        event1.addAirDateTime(dateTime1, auditoriumService.getByName("Red"));*/

        Event event2 = new Event("Black sails", 90, EventRating.MID);
/*        LocalDateTime dateTime2 = LocalDateTime.now().plusDays(4);
        event2.addAirDateTime(dateTime2, auditoriumService.getByName("Yellow"));*/

        Set<Event> events = new HashSet<>();
        events.add(event1);
        events.add(event2);

        return events;
    }

}