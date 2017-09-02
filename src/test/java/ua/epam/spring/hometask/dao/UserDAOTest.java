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

        if (!userDAO.isTableExist(userDAO.tableName)) {
            String sql = "create table " + userDAO.tableName + " " +
                    "(id integer, " +
                    "msg varchar(255), " +
                    "firstName varchar(50), " +
                    "lastName varchar(50), " +
                    "email varchar(50))";

            userDAO.jdbcTemplate.execute(sql);
        }
    }

    @After
    public void tearDown() throws Exception {
        try {
            DriverManager.getConnection("jdbc:derby:memory:db;drop=true");
        } catch (SQLException e) {
            if (!"08006".equals(e.getSQLState())) {
                throw e;
            }
        }
    }


    @Test
    public void save() {
        assumeTrue("Table should be empty before each test", userDAO.getAll().isEmpty());

        User user = new User();
        userDAO.save(user);
        assertTrue("Should contain user after save: " + user, userDAO.getAll().contains(user));
    }

    @Test
    public void remove() throws Exception {
        assumeTrue("Table should be empty before each test", userDAO.getAll().isEmpty());

        User user = new User();
        user.setId(1L);
        userDAO.save(user);

        userDAO.remove(user);

        assertTrue("Table should not contain removed user", !userDAO.getAll().contains(user));
    }


}