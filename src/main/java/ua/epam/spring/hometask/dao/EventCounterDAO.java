package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class EventCounterDAO {
    private JdbcTemplate jdbcTemplate;

    public synchronized void addNameAccessCounter() {
        Long counter = jdbcTemplate.queryForObject("select nameAccessCounter from t_event_counter", Long.class);
        jdbcTemplate.update("update t_event_counter set nameAccessCounter = ?", ++counter);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long getNameAccessCounter() {
        return jdbcTemplate.queryForObject("select nameAccessCounter from t_event_counter", Long.class);
    }
}
