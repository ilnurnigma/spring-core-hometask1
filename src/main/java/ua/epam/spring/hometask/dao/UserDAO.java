package ua.epam.spring.hometask.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class UserDAO extends DomainObjectDAO<User> {
    public @Nullable
    User getUserByEmail(@Nonnull String email) {
        String sql = "select id, firstName, lastName, email, dateOfBirth from " + tableName + " where email=?";
        try {
            return jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> getUser(resultSet), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User save(User object) {
        User userByEmail = getUserByEmail(object.getEmail());
        if (userByEmail != null) {
            return userByEmail;
        }

        String sql = "insert into " + tableName + " " +
                "(firstName, lastName, email, dateOfBirth) values (?, ?, ?, ?)";

        Date dateOfBirth = object.getDateOfBirth() != null ? Date.valueOf(object.getDateOfBirth()) : null;

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"ID"});
            statement.setString(1, object.getFirstName());
            statement.setString(2, object.getLastName());
            statement.setString(3, object.getEmail());
            statement.setDate(4, dateOfBirth);
            return statement;
        }, keyHolder);

        object.setId(keyHolder.getKey().longValue());
        return object;
    }

    @Override
    public boolean remove(User object) {
        String sql = "delete from " + tableName + " where id=?";
        jdbcTemplate.update(sql, object.getId());
        return true;
    }

    @Override
    public User getById(@Nonnull Long id) {
        String sql = "select id, firstName, lastName, email, dateOfBirth from " + tableName + " where id=?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> getUser(resultSet), id);
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setEmail(resultSet.getString("email"));
        Date dateOfBirth = resultSet.getDate("dateOfBirth");
        user.setDateOfBirth(dateOfBirth != null ? dateOfBirth.toLocalDate() : null);
        return user;
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        String sql = "select id, firstName, lastName, email, dateOfBirth from " + tableName;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

}
