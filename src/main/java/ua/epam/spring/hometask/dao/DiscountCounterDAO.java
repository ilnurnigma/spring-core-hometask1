package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.epam.spring.hometask.domain.User;

/**
 * Created on 9/4/2017.
 */
public class DiscountCounterDAO {
    private String tableName;
    private JdbcTemplate jdbcTemplate;

    public synchronized void addDiscountCounter(User user, String strategy) {
        if (user == null) {
            addTotalCounter(strategy);
            return;
        }

        if (!isExist(user, strategy)) {
            init(user, strategy);
        }

        Long counter = getDiscountCounter(user, strategy);
        String sql = "update " + tableName + " set discountCounter = ? where userId = ? and strategy = ?";
        jdbcTemplate.update(sql, ++counter, user.getId(), strategy);
    }

    public synchronized void addTotalCounter(String strategy) {
        if (!isExist(strategy)) {
            init(strategy);
        }

        Long counter = getTotalCounter(strategy);
        String sql = "update " + tableName + " set discountCounter = ? where userId = ? and strategy = ?";
        jdbcTemplate.update(sql, ++counter, "", strategy);
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private boolean isExist(User user, String strategy) {
        if (user == null) {
            return isExist(strategy);
        }

        String sql = "select count(*) from " + tableName + " where userId = ? and strategy = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, user.getId(), strategy) != 0;
    }

    private boolean isExist(String strategy) {
        String sql = "select count(*) from " + tableName + " where strategy = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, strategy) != 0;
    }

    private void init(User user, String strategy) {
        if (user == null) {
            init(strategy);
            return;
        }

        String sql = "insert into " + tableName +
                " (userId, discountCounter, strategy)" +
                " values (?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), 0, strategy);
    }

    private void init(String strategy) {
        String sql = "insert into " + tableName +
                " (userId, discountCounter, strategy)" +
                " values (?, ?, ?)";
        jdbcTemplate.update(sql, "", 0, strategy);
    }

    public long getDiscountCounter(User user, String strategy) {
        if (user == null) {
            return getTotalCounter(strategy);
        }

        if (!isExist(user, strategy)) {
            return 0;
        }

        String sql = "select discountCounter from " + tableName + " where userId = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, user.getId());
    }

    public long getTotalCounter(String strategy) {
        if (!isExist(strategy)) {
            return 0;
        }

        String sql = "select sum(discountCounter) from " + tableName + " where strategy = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, strategy);
    }
}
