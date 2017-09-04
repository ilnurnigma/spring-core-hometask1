package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTestHelper {

    public static void createUserDB(JdbcTemplate jdbcTemplate) throws SQLException {
        String sql = "create table t_user " +
                "(id int not null primary key generated always as identity (start with 1, increment by 1), " +
                "firstName varchar(50), " +
                "lastName varchar(50), " +
                "email varchar(50), " +
                "dateOfBirth date)";

        jdbcTemplate.execute(sql);
    }

    public static void createTicketDB(JdbcTemplate jdbcTemplate) throws SQLException {
        String sql = "create table t_ticket " +
                "(id int not null primary key generated always as identity (start with 1, increment by 1)";
        StringBuilder sb = new StringBuilder(sql);
        sb.append(", seat int");
        sb.append(", dateTime timestamp");
        sb.append(", userId int references t_user(id)");
        sb.append(", eventId int references t_event(id)");
        sb.append(")");

        jdbcTemplate.execute(sb.toString());
    }

    public static void createEventDB(JdbcTemplate jdbcTemplate) throws SQLException {
        String sql = "create table t_event " +
                "(id int not null primary key generated always as identity (start with 1, increment by 1)";
        StringBuilder sb = new StringBuilder(sql);
        sb.append(", name varchar(255)");
        sb.append(", basePrice double");
        sb.append(", rating varchar(100)");
        sb.append(")");

        jdbcTemplate.execute(sb.toString());
    }

    public static void createAirdateDB(JdbcTemplate jdbcTemplate) throws SQLException {
        String sql = "create table t_airdate " +
                "(id int not null primary key generated always as identity (start with 1, increment by 1)";
        StringBuilder sb = new StringBuilder(sql);
        sb.append(", dateTime timestamp");
        sb.append(", eventId int references t_event(id)");
        sb.append(")");

        jdbcTemplate.execute(sb.toString());
    }

    public static void createAuditoriumDB(JdbcTemplate jdbcTemplate) throws SQLException {
        String sql = "create table t_auditorium " +
                "(id int not null primary key generated always as identity (start with 1, increment by 1)";
        StringBuilder sb = new StringBuilder(sql);
        sb.append(", name varchar(200)");
        sb.append(", dateTime timestamp");
        sb.append(", eventId int references t_event(id)");
        sb.append(")");

        jdbcTemplate.execute(sb.toString());
    }

    public static void createEventCounterDB(JdbcTemplate jdbcTemplate) throws SQLException {
        String sql = "create table t_event_counter " +
                "(id int not null primary key generated always as identity (start with 1, increment by 1)";
        StringBuilder sb = new StringBuilder(sql);
        sb.append(", nameAccessCounter int");
        sb.append(", priceAccessCounter int");
        sb.append(", bookTicketCounter int");
        sb.append(", eventId int");
        sb.append(")");

        jdbcTemplate.execute(sb.toString());
    }

    public static void createDiscountCounterDB(JdbcTemplate jdbcTemplate) throws SQLException {
        String sql = "create table t_discount_counter " +
                "(id int not null primary key generated always as identity (start with 1, increment by 1)";
        StringBuilder sb = new StringBuilder(sql);
        sb.append(", discountCounter int");
        sb.append(", userId varchar(100)");
        sb.append(", strategy varchar(255)");
        sb.append(")");

        jdbcTemplate.execute(sb.toString());
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

    public static void createDB(JdbcTemplate jdbcTemplate) throws SQLException {
        createEventDB(jdbcTemplate);
        createUserDB(jdbcTemplate);
        createTicketDB(jdbcTemplate);
        createAirdateDB(jdbcTemplate);
        createEventCounterDB(jdbcTemplate);
        createAuditoriumDB(jdbcTemplate);
        createDiscountCounterDB(jdbcTemplate);
    }
}
