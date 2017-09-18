package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;

import javax.annotation.Nonnull;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 8/23/2017.
 */
public class TicketDAO extends DomainObjectDAO<Ticket> {
    private UserDAO userDAO;
    private EventDAO eventDAO;

    public boolean addAll(Set<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            save(ticket);
        }

        return true;
    }

    /**
     * Getting all purchased domainObjects for event on specific air date and time
     *
     * @param event    Event to get domainObjects for
     * @param dateTime Date and time of airing of event
     * @return set of all purchased domainObjects
     */
    public @Nonnull
    Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        StringBuilder sql = new StringBuilder();
        sql.append("select id, seat, dateTime, userId, eventId from " + tableName);
        sql.append(" where eventId = " + event.getId());
        sql.append(" and dateTime = '" + Timestamp.valueOf(dateTime) + "'");
        List<Ticket> tickets = jdbcTemplate.query(sql.toString(), (resultSet, rowNumber) -> getTicket(resultSet));

        return new HashSet<>(tickets);
    }

    @Override
    public Ticket save(Ticket object) {
        String sql = "insert into " + tableName + " " +
                "(seat, dateTime, userId, eventId) values (?, ?, ?, ?)";

        LocalDateTime dateTime = object.getDateTime();
        Timestamp timestamp = dateTime != null ? Timestamp.valueOf(dateTime) : null;
        long eventId = object.getEvent().getId();

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"ID"});
            statement.setLong(1, object.getSeat());
            statement.setTimestamp(2, timestamp);

            if (object.getUser() != null) {
                statement.setLong(3, object.getUser().getId());
            } else {
                statement.setNull(3, Types.INTEGER);
            }

            statement.setLong(4, eventId);
            return statement;
        }, keyHolder);

        object.setId(keyHolder.getKey().longValue());
        return object;
    }

    @Nonnull
    @Override
    public Collection<Ticket> getAll() {
        String sql = "select id, seat, dateTime, userId, eventId from " + tableName;
        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> getTicket(resultSet));
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public Ticket getById(@Nonnull Long id) {
        String sql = "select id, seat, dateTime, userId, eventId from " + tableName + " where id=?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> getTicket(resultSet), id);
    }

    private Ticket getTicket(ResultSet resultSet) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(resultSet.getLong("id"));
        ticket.setSeat(resultSet.getLong("seat"));
        ticket.setDateTime(resultSet.getTimestamp("dateTime").toLocalDateTime());
        ticket.setUser(userDAO.getById(resultSet.getLong("userId")));
        ticket.setEvent(eventDAO.getById(resultSet.getLong("eventId")));
        return ticket;
    }
}
