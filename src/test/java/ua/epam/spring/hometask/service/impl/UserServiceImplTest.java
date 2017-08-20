package ua.epam.spring.hometask.service.impl;

import org.junit.Before;
import org.junit.Test;
import ua.epam.spring.hometask.dao.Users;
import ua.epam.spring.hometask.domain.User;

import java.util.Collection;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class UserServiceImplTest {

    private UserServiceImpl userService;
    private User user;

    @Before
    public void setUp() throws Exception {
        userService = new UserServiceImpl();

        user = new User();
        user.setFirstName("John");
        user.setLastName("Snow");
        user.setEmail("john_snow@epam.com");
        user.setId(1L);
        user.setTickets(new TreeSet<>());
    }

    @Test
    public void givenEmailReturnUser() throws Exception {
        User expectedUser = new User();
        expectedUser.setEmail("emailname@epam.com");
        Users.getUsers().add(expectedUser);

        User actualUser = userService.getUserByEmail("emailname@epam.com");

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void givenEmail2ReturnUser() throws Exception {
        User expectedUser = new User();
        expectedUser.setEmail("emailname2@epam.com");
        Users.getUsers().add(expectedUser);

        User actualUser = userService.getUserByEmail("emailname2@epam.com");

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void givenUserJohnShouldSave() throws Exception {
        userService.save(user);

        assertTrue(Users.getUsers().contains(user));
    }

    @Test
    public void givenUserShouldRemove() throws Exception {
        Users.getUsers().add(user);

        userService.remove(user);

        assertFalse(Users.getUsers().contains(user));
    }

    @Test
    public void givenUserReturnById() throws Exception {
        Users.getUsers().add(user);

        User actualUser = userService.getById(1L);

        assertEquals(user, actualUser);
    }

    @Test
    public void getAll() throws Exception {
        User user2 = new User();
        user2.setFirstName("Daenerys");
        user2.setLastName("Targaryen");
        user2.setEmail("enerys_targaryen@epam.com");
        user2.setId(2L);
        user2.setTickets(new TreeSet<>());

        Users.getUsers().add(user);
        Users.getUsers().add(user2);

        Collection<User> users = userService.getAll();

        assertEquals(Users.getUsers(), users);
    }

}