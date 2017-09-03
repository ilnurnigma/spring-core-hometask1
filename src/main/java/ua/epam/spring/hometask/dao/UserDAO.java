package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

public class UserDAO extends DomainObjectDAO<User> {
    public @Nullable
    User getUserByEmail(@Nonnull String email) {
        String sql = "select id, firstName, lastName, email, dateOfBirth from " + tableName + " where email=?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> getUser(resultSet), email);
    }

    @Override
    public boolean save(User object) {
        String sql = "insert into " + tableName + " " +
                "(firstName, lastName, email, dateOfBirth) values (?, ?, ?, ?)";

        Date dateOfBirth = object.getDateOfBirth() != null ? Date.valueOf(object.getDateOfBirth()) : null;
        jdbcTemplate.update(sql, object.getFirstName(), object.getLastName(),
                object.getEmail(), dateOfBirth);

        return true;
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
