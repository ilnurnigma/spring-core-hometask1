package ua.epam.spring.hometask.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.User;

import java.util.Collection;
import java.util.TreeSet;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class UserServiceImplTest {
    @Autowired
    private ApplicationContext ctx;

    private UserServiceImpl service;
    private User user;
    private User user2;

    @Before
    public void setUp() throws Exception {
        service = ctx.getBean("userServiceImpl", UserServiceImpl.class);

        user = new User();
        user.setFirstName("John");
        user.setLastName("Snow");
        user.setEmail("john_snow@epam.com");
        user.setId(1L);
        user.setTickets(new TreeSet<>());

        user2 = new User();
        user.setFirstName("Daenerys");
        user.setLastName("Targaryen");
        user.setEmail("daenerys_targaryen@epam.com");
        user.setId(1L);
        user.setTickets(new TreeSet<>());
    }

    @Test
    public void givenApplicationContextReturnBeanImplementation() {
        assertTrue(ctx.getBean("userServiceImpl") instanceof UserServiceImpl);
    }

    @Test
    public void givenEmailReturnUser() throws Exception {
        service.save(user);
        User actualUser = service.getUserByEmail(user.getEmail());
        assertEquals(user, actualUser);
    }

    @Test
    public void givenTwoUsersReturnUserByEmail() throws Exception {
        service.save(user);
        service.save(user2);

        User actualUser = service.getUserByEmail(user.getEmail());
        assertEquals(user, actualUser);
    }

    @Test
    public void givenUserJohnShouldSave() throws Exception {
        service.save(user);
        assertTrue(service.getAll().contains(user));
    }

    @Test
    public void givenUserShouldRemove() throws Exception {
        service.save(user);
        service.remove(user);
        assertFalse(service.getAll().contains(user));
    }

    @Test
    public void givenUserReturnById() throws Exception {
        service.save(user);
        User actualUser = service.getById(1L);
        assertEquals(user, actualUser);
    }
}