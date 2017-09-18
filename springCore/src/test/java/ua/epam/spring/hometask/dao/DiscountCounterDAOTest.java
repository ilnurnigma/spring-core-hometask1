package ua.epam.spring.hometask.dao;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.strategies.Every10thTicketDiscountStrategy;
import ua.epam.spring.hometask.util.DBCreator;

import static org.junit.Assert.assertEquals;

/**
 * Created on 9/4/2017.
 */
public class DiscountCounterDAOTest {

    private DiscountCounterDAO discountCounterDAO;

    @Before
    public void setUp() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(EmbeddedDriver.class.getName());
        dataSource.setUrl("jdbc:derby:memory:db;create=true");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        DBCreator.createDiscountCounterDB(jdbcTemplate);

        discountCounterDAO = new DiscountCounterDAO();
        discountCounterDAO.setJdbcTemplate(jdbcTemplate);
        discountCounterDAO.setTableName("t_discount_counter");
    }

    @After
    public void tearDown() throws Exception {
        DBCreator.dropDB();
    }

    @Test
    public void save() throws Exception {
        User user = new User();
        user.setId(1L);

        String strategy = Every10thTicketDiscountStrategy.class.getName();
        discountCounterDAO.addDiscountCounter(user, strategy);
        discountCounterDAO.addDiscountCounter(user, strategy);
        discountCounterDAO.addDiscountCounter(user, strategy);

        assertEquals(3, discountCounterDAO.getDiscountCounter(user, strategy));
    }

    @Test
    public void getTotalCounter() {
        User user = new User();
        user.setId(1L);

        String strategy = Every10thTicketDiscountStrategy.class.getName();
        discountCounterDAO.addDiscountCounter(user, strategy);
        discountCounterDAO.addDiscountCounter(user, strategy);
        discountCounterDAO.addDiscountCounter(user, strategy);

        assertEquals(3, discountCounterDAO.getTotalCounter(strategy));
    }
}