package ua.epam.spring.hometask.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import ua.epam.spring.hometask.domain.User;

public class UserAccountDAO {
    protected JdbcOperations jdbcTemplate;
    protected String tableName;

    public void setJdbcTemplate(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public double add(User user, double amount) {
        if (!isExist(user)) {
            init(user, amount);
            return amount;
        }

        String sql = "update " + tableName + " set amount = ? where userId = ?";
        double total = getAmount(user) + amount;
        jdbcTemplate.update(sql, total, user.getId());

        return total;
    }

    private boolean isExist(User user) {
        String sql = "select count(*) from " + tableName + " where userId = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, user.getId()) != 0;
    }

    private void init(User user, double amount) {
        String sql = "insert into " + tableName +
                " (userId, amount)" +
                " values (?, ?)";
        jdbcTemplate.update(sql, user.getId(), amount);
    }

    public double getAmount(User user) {
        String sql = "select amount from " + tableName + " where userId = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Double.class, user.getId());
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public double subtractAmount(User user, double amount) {
        double total = getAmount(user);
        if (total < amount) {
            return total;
        }

        String sql = "update " + tableName + " set amount = ? where userId = ?";
        total = total - amount;
        jdbcTemplate.update(sql, total, user.getId());

        return total;
    }
}
