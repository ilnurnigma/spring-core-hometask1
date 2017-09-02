package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
        String sql = "select id, firstName, lastName, email from " + tableName;

        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        System.out.println(users);

        return users;
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
