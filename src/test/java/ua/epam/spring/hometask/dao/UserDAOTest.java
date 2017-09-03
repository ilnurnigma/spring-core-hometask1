package ua.epam.spring.hometask.dao;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.epam.spring.hometask.domain.User;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.*;

public class UserDAOTest {
    private UserDAO userDAO;

    @Before
    public void setUp() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(EmbeddedDriver.class.getName());
        dataSource.setUrl("jdbc:derby:memory:db;create=true");

        userDAO = new UserDAO();
        userDAO.setJdbcTemplate(new JdbcTemplate(dataSource));
        userDAO.setTableName("t_user");

        DBTestHelper.createUserDB(userDAO.jdbcTemplate);
    }

    @After
    public void tearDown() throws Exception {
        DBTestHelper.dropDB();
    }


    @Test
    public void save() {
        assumeTrue("Table should be empty before each test", userDAO.getAll().isEmpty());

        User user = new User();
        userDAO.save(user);
        assertTrue("Should contain user after save: " + user, userDAO.getAll().contains(user));
    }

    @Test
    public void remove() {
        assumeTrue("Table should be empty before each test", userDAO.getAll().isEmpty());

        User user = new User();
        userDAO.save(user);
        user = userDAO.getAll().iterator().next();

        userDAO.remove(user);

        assertTrue("Table should not contain removed user", !userDAO.getAll().contains(user));
    }

    @Test
    public void getById() {
        assumeTrue("Table should be empty before each test", userDAO.getAll().isEmpty());

        User user = new User();
        userDAO.save(user);
        user = userDAO.getAll().iterator().next();

        User userById = userDAO.getById(1L);

        assertEquals(user, userById);
    }

    @Test
    public void getUserByEmail() {
        assumeTrue("Table should be empty before each test", userDAO.getAll().isEmpty());

        User expectedUser = new User();
        String email = "john_snow@epam.com";
        expectedUser.setEmail(email);
        userDAO.save(expectedUser);

        User userByEmail = userDAO.getUserByEmail(email);

        assertEquals(expectedUser, userByEmail);
    }

}