package ua.epam.spring.hometask.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.epam.spring.hometask.domain.Event;

public class EventCounterDAO {
    private String tableName;
    private JdbcOperations jdbcTemplate;

    public synchronized void addNameAccessCounter(Event event) {
        if (!isExist(event)) {
            init(event);
        }

        Long counter = getNameAccessCounter(event);
        String sql = "update " + tableName + " set nameAccessCounter = ? where eventId = ?";
        jdbcTemplate.update(sql, ++counter, event.getId());
    }

    public synchronized void addPriceAccesCounter(Event event) {
        if (!isExist(event)) {
            init(event);
        }

        Long counter = getPriceAccesCounter(event);
        String sql = "update " + tableName + " set priceAccessCounter = ? where eventId = ?";
        jdbcTemplate.update(sql, ++counter, event.getId());
    }

    public synchronized void addBookTicketCounter(Event event) {
        if (!isExist(event)) {
            init(event);
        }

        Long counter = getBookTicketCounter(event);
        String sql = "update " + tableName + " set bookTicketCounter = ? where eventId = ?";
        jdbcTemplate.update(sql, ++counter, event.getId());
    }

    private void init(Event event) {
        String sql = "insert into " + tableName +
                " (eventId, nameAccessCounter, priceAccessCounter, bookTicketCounter)" +
                " values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, event.getId(), 0, 0, 0);
    }

    private boolean isExist(Event event) {
        if (event == null) {
            return false;
        }

        String sql = "select count(*) from " + tableName + " where eventId = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, event.getId()) != 0;
    }

    public void setJdbcTemplate(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long getNameAccessCounter(Event event) {
        if (!isExist(event)) {
            return 0;
        }

        String sql = "select nameAccessCounter from " + tableName + " where eventId = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, event.getId());
    }

    public long getPriceAccesCounter(Event event) {
        if (!isExist(event)) {
            return 0;
        }

        String sql = "select priceAccessCounter from " + tableName + " where eventId = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, event.getId());
    }

    public long getBookTicketCounter(Event event) {
        if (!isExist(event)) {
            return 0;
        }

        String sql = "select bookTicketCounter from " + tableName + " where eventId = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, event.getId());
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
