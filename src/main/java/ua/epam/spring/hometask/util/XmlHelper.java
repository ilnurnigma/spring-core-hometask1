package ua.epam.spring.hometask.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * Created on 9/6/2017.
 */
public class XmlHelper {
    private BatchUpload upload;

    @Autowired
    private Jaxb2Marshaller marshaller;

    public void setMarshaller(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public void unmarshal(InputStream inputStream) {
        upload = (BatchUpload) marshaller.unmarshal(new StreamSource(inputStream));
    }

    public Set<User> getUsers() {
        return upload.getUsers();
    }

    public Set<Event> getEvents() {
        return upload.getEvents();
    }
}
