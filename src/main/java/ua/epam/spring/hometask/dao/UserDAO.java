package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.RowMapper;
import ua.epam.spring.hometask.domain.DomainObject;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class UserDAO extends DomainObjectDAO<User> {
    public @Nullable
    User getUserByEmail(@Nonnull String email) {
        for (User user : domainObjects) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }

        return null;
    }

    @Override
    public boolean save(User object) {
        String sql = "insert into " + tableName + " " +
                "(id, msg, firstName, lastName, email) values (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, object.getId(), "some message",
                object.getFirstName(), object.getLastName(), object.getEmail());
        return domainObjects.add(object);
    }

    @Override
    public boolean remove(User object) {
        return super.remove(object);
    }

    @Override
    public User getById(@Nonnull Long id) {
        String sql = "select id, msg, firstName, lastName, email from " + tableName + " where id=?";

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
        return super.getAll();
    }

    @Override
    public void init() throws SQLException {
        if (!isTableExist(tableName)) {
            String sql = "create table " + tableName + " " +
                    "(id integer, " +
                    "msg varchar(255), " +
                    "firstName varchar(50), " +
                    "lastName varchar(50), " +
                    "email varchar(50))";

            jdbcTemplate.execute(sql);
        }
    }
}
