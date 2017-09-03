package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTestHelper {

    public static void createUserDB(JdbcTemplate jdbcTemplate) throws SQLException {
        String sql = "create table t_user " +
                "(id integer not null primary key generated always as identity (start with 1, increment by 1), " +
                "msg varchar(255), " +
                "firstName varchar(50), " +
                "lastName varchar(50), " +
                "email varchar(50))";

        jdbcTemplate.execute(sql);
    }

    public static void dropDB() throws SQLException {
        try {
            DriverManager.getConnection("jdbc:derby:memory:db;drop=true");
        } catch (SQLException e) {
            if (!"08006".equals(e.getSQLState())) {
                throw e;
            }
        }
    }
}
