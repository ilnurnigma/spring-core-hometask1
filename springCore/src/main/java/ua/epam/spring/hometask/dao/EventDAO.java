package ua.epam.spring.hometask.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.service.AuditoriumService;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created on 8/23/2017.
 */
public class EventDAO extends DomainObjectDAO<Event> {
    private AuditoriumService auditoriumService;
    private String airdatesTableName;
    private String auditoriumsTableName;

    public @Nonnull
    Set<Event> getForDateRange(@Nonnull LocalDate from,
                               @Nonnull LocalDate to) {

        StringBuilder sql = new StringBuilder();
        sql.append("select e.id, e.name, e.basePrice, e.rating from " + tableName + " e");
        sql.append(" inner join " + airdatesTableName + " ai");
        sql.append(" on e.id = ai.eventId");
        sql.append(" where ai.dateTime between '" + Timestamp.valueOf(from.atStartOfDay()) + "'");
        sql.append(" and '" + Timestamp.valueOf(to.atStartOfDay()) + "'");

        List<Event> eventList = jdbcTemplate.query(sql.toString(), (resultSet, i) -> getEvent(resultSet));
        return new HashSet<>(eventList);
    }

    public @Nonnull
    Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        return getForDateRange(LocalDate.now(), to.toLocalDate());
    }

    public Event getByName(String name) {
        String sql = "select id, name, basePrice, rating from " + tableName + " where name=?";
        try {
            return jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> getEvent(resultSet), name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Event save(Event object) {
        String sql;
        if (object.getId() == null) {
            sql = "insert into " + tableName + " " +
                    "(name, basePrice, rating) values (?, ?, ?)";
        } else {
            sql = "update " + tableName + " set name = ?, basePrice = ?, rating = ?";
        }

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String rating = object.getRating() != null ? object.getRating().toString() : EventRating.MID.toString();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"ID"});
            statement.setString(1, object.getName());
            statement.setDouble(2, object.getBasePrice());
            statement.setString(3, rating);
            return statement;
        }, keyHolder);

        if (object.getId() == null) {
            object.setId(keyHolder.getKey().longValue());
        }

        saveAirdates(object);
        saveAuditoriums(object);

        return object;
    }

    private void saveAirdates(Event event) {
        jdbcTemplate.batchUpdate("insert into " + airdatesTableName + " (dateTime, eventId) values (?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                LocalDateTime dateTime = event.getAirDates().iterator().next();
                preparedStatement.setTimestamp(1, Timestamp.valueOf(dateTime));
                preparedStatement.setLong(2, event.getId());
            }

            @Override
            public int getBatchSize() {
                return event.getAirDates().size();
            }
        });
    }

    private void saveAuditoriums(Event event) {
        jdbcTemplate.batchUpdate("insert into " + auditoriumsTableName + " (eventId, name, dateTime) values (?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                LocalDateTime dateTime = event.getAuditoriums().keySet().iterator().next();
                Auditorium auditorium = event.getAuditoriums().get(dateTime);
                preparedStatement.setLong(1, event.getId());
                preparedStatement.setString(2, auditorium.getName());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(dateTime));
            }

            @Override
            public int getBatchSize() {
                return event.getAuditoriums().size();
            }
        });
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        String sql = "select id, name, basePrice, rating from " + tableName;
        return jdbcTemplate.query(sql, (resultSet, i) -> getEvent(resultSet));
    }

    private NavigableSet<LocalDateTime> getAirdates(Long id) {
        TreeSet<LocalDateTime> airdates = new TreeSet<>();
        String sql = "select eventId, dateTime from " + airdatesTableName + " where eventId=?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, id);
        while (sqlRowSet.next()) {
            Timestamp dateTime = sqlRowSet.getTimestamp("dateTime");
            airdates.add(dateTime.toLocalDateTime());
        }

        return airdates;
    }

    private NavigableMap<LocalDateTime, Auditorium> getAuditoriums(Long id) {
        TreeMap<LocalDateTime, Auditorium> auditoriumMap = new TreeMap<>();
        String sql = "select eventId, name, dateTime from " + auditoriumsTableName + " where eventId=?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, id);
        while (sqlRowSet.next()) {
            String name = sqlRowSet.getString("name");
            Timestamp dateTime = sqlRowSet.getTimestamp("dateTime");
            auditoriumMap.put(dateTime.toLocalDateTime(), auditoriumService.getByName(name));
        }

        return auditoriumMap;
    }

    public void setAirdatesTableName(String airdatesTableName) {
        this.airdatesTableName = airdatesTableName;
    }

    public void setAuditoriumsTableName(String auditoriumsTableName) {
        this.auditoriumsTableName = auditoriumsTableName;
    }

    public void setAuditoriumService(AuditoriumService auditoriumService) {
        this.auditoriumService = auditoriumService;
    }

    @Override
    public Event getById(@Nonnull Long id) {
        String sql = "select id, name, basePrice, rating from " + tableName + " where id=?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> getEvent(resultSet), id);
    }

    private Event getEvent(ResultSet resultSet) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getLong("id"));
        event.setName(resultSet.getString("name"));
        event.setBasePrice(resultSet.getDouble("basePrice"));
        event.setRating(EventRating.valueOf(resultSet.getString("rating")));
        event.setAirDates(getAirdates(event.getId()));
        event.setAuditoriums(getAuditoriums(event.getId()));
        return event;
    }
}
