package ua.epam.spring.hometask.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.UserAccount;

public class UserAccountDAO {
    private JdbcOperations jdbcTemplate;
    private String tableName;

    public void setJdbcTemplate(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public UserAccount refill(UserAccount account, double amount) {
        if (!isExist(account)) {
            init(account, amount);
            account.setAmount(amount);
            return account;
        }

        String sql = "update " + tableName + " set amount = ? where userId = ?";
        double total = getAmount(account.getUser()) + amount;
        jdbcTemplate.update(sql, total, account.getUser().getId());

        account.setAmount(total);
        return account;
    }

    private boolean isExist(UserAccount account) {
        String sql = "select count(*) from " + tableName + " where userId = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, account.getUser().getId()) != 0;
    }

    private void init(UserAccount account, double amount) {
        String sql = "insert into " + tableName +
                " (userId, amount)" +
                " values (?, ?)";
        jdbcTemplate.update(sql, account.getUser().getId(), amount);
    }

    private double getAmount(User user) {
        String sql = "select amount from " + tableName + " where userId = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Double.class, user.getId());
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public UserAccount subtract(UserAccount account, double amount) {
        double total = getAmount(account.getUser());
        if (total < amount) {
            return account;
        }

        String sql = "update " + tableName + " set amount = ? where userId = ?";
        total = total - amount;
        jdbcTemplate.update(sql, total, account.getUser().getId());

        account.setAmount(total);
        return account;
    }

    public UserAccount getAccount(User user) {
        return new UserAccount(user, getAmount(user));
    }
}
