package ua.epam.spring.hometask.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.multipart.MultipartFile;
import ua.epam.spring.hometask.domain.User;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.List;

/**
 * Created on 9/6/2017.
 */
public class XmlHelper {

    @Autowired
    private Jaxb2Marshaller marshaller;

    public void setMarshaller(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public User getUser(MultipartFile file) throws IOException {
        return (User) marshaller.unmarshal(new StreamSource(file.getInputStream()));
    }
}
