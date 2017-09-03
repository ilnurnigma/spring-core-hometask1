package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public class UserDAO extends DomainObjectDAO<User> {
    public @Nullable
    User getUserByEmail(@Nonnull String email) {
        String sql = "select id, firstName, lastName, email from " + tableName + " where email=?";

        return jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setFirstName(resultSet.getString("firstName"));
            user.setLastName(resultSet.getString("lastName"));
            user.setEmail(resultSet.getString("email"));
            return user;
        }, email);
    }

    @Override
    public boolean save(User object) {
        String sql = "insert into " + tableName + " " +
                "(firstName, lastName, email) values (?, ?, ?)";

        jdbcTemplate.update(sql, object.getFirstName(), object.getLastName(), object.getEmail());

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
        String sql = "select id, firstName, lastName, email from " + tableName + " where id=?";

        return jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setFirstName(resultSet.getString("firstName"));
            user.setLastName(resultSet.getString("lastName"));
            user.setEmail(resultSet.getString("email"));
            return user;
        }, id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        String sql = "select id, firstName, lastName, email from " + tableName;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

}
