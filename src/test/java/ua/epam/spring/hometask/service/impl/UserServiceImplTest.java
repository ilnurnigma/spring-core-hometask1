package ua.epam.spring.hometask.service.impl;

import org.junit.Before;
import org.junit.Test;
import ua.epam.spring.hometask.dao.Users;
import ua.epam.spring.hometask.domain.User;

import java.util.TreeSet;

import static org.junit.Assert.*;

public class UserServiceImplTest {

    private UserServiceImpl userService;

    @Before
    public void setUp() throws Exception {
        userService = new UserServiceImpl();
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
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Snow");
        user.setEmail("john_snow@epam.com");
        user.setId(1L);
        user.setTickets(new TreeSet<>());

        userService.save(user);

        assertTrue(Users.getUsers().contains(user));
    }

    @Test
    public void remove() throws Exception {
    }

    @Test
    public void getById() throws Exception {
    }

    @Test
    public void getAll() throws Exception {
    }

}