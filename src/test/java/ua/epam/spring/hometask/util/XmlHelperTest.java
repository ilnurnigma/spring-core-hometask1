package ua.epam.spring.hometask.util;

import org.junit.Test;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ua.epam.spring.hometask.domain.User;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created on 9/6/2017.
 */
public class XmlHelperTest {
    @Test
    public void marshall() throws FileNotFoundException {
        FileOutputStream outputStream = new FileOutputStream("users.xml");

        ArrayList<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setFirstName("John");

        User user2 = new User();
        user2.setFirstName("Daenerys");
        user2.setLastName("Targaryen");

        users.add(user1);
        users.add(user2);

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(new Class[]{ua.epam.spring.hometask.util.XmlHelper.Users.class});
        marshaller.setMarshallerProperties(new HashMap<String, Object>() {{
            put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
        }});
        marshaller.marshal(users, new StreamResult(outputStream));

        Object user = marshaller.unmarshal(new StreamSource(new FileInputStream("users.xml")));
        System.out.println(user);
    }

}