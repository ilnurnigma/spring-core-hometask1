package ua.epam.spring.hometask.util;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement
public class BatchUpload {

    private Set<User> users;
    private Set<Event> events;

    public Set<User> getUsers() {
        return users;
    }

    @XmlElementWrapper
    @XmlElement(name = "user")
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @XmlElementWrapper
    @XmlElement(name = "event")
    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "BatchUpload{" +
                "users=" + users +
                ", events=" + events +
                '}';
    }
}
