package ua.epam.spring.hometask.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCreator {
    private JdbcOperations jdbcTemplate;

    public void setJdbcTemplate(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void init() throws SQLException {
        createDB(jdbcTemplate);
    }

    public static void createUserDB(JdbcOperations jdbcTemplate) throws SQLException {
        String sql = "create table t_user " +
                "(id int not null primary key generated always as identity (start with 1, increment by 1), " +
                "firstName varchar(50), " +
                "lastName varchar(50), " +
                "email varchar(50), " +
                "password varchar(255), " +
                "roles varchar(255), " +
                "dateOfBirth date)";

        jdbcTemplate.execute(sql);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
        jdbcTemplate.update("insert into t_user (email, password, roles) values (?, ?, ?)"
                , "user@mail.com"
                , encoder.encode("12345")
                , "ROLE_REGISTERED_USER");

        jdbcTemplate.update("insert into t_user (email, password, roles) values (?, ?, ?)"
                , "manager@mail.com"
                , encoder.encode("12345")
                , "ROLE_REGISTERED_USER,ROLE_BOOKING_MANAGER");
    }

    public static void createTicketDB(JdbcOperations jdbcTemplate) throws SQLException {
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

    public static void createEventDB(JdbcOperations jdbcTemplate) throws SQLException {
        String sql = "create table t_event " +
                "(id int not null primary key generated always as identity (start with 1, increment by 1)";
        StringBuilder sb = new StringBuilder(sql);
        sb.append(", name varchar(255)");
        sb.append(", basePrice double");
        sb.append(", rating varchar(100)");
        sb.append(")");

        jdbcTemplate.execute(sb.toString());
    }

    public static void createAirdateDB(JdbcOperations jdbcTemplate) throws SQLException {
        String sql = "create table t_airdate " +
                "(id int not null primary key generated always as identity (start with 1, increment by 1)";
        StringBuilder sb = new StringBuilder(sql);
        sb.append(", dateTime timestamp");
        sb.append(", eventId int references t_event(id)");
        sb.append(")");

        jdbcTemplate.execute(sb.toString());
    }

    public static void createAuditoriumDB(JdbcOperations jdbcTemplate) throws SQLException {
        String sql = "create table t_auditorium " +
                "(id int not null primary key generated always as identity (start with 1, increment by 1)";
        StringBuilder sb = new StringBuilder(sql);
        sb.append(", name varchar(200)");
        sb.append(", dateTime timestamp");
        sb.append(", eventId int references t_event(id)");
        sb.append(")");

        jdbcTemplate.execute(sb.toString());
    }

    public static void createEventCounterDB(JdbcOperations jdbcTemplate) throws SQLException {
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

    public static void createDiscountCounterDB(JdbcOperations jdbcTemplate) throws SQLException {
        String sql = "create table t_discount_counter " +
                "(id int not null primary key generated always as identity (start with 1, increment by 1)";
        StringBuilder sb = new StringBuilder(sql);
        sb.append(", discountCounter int");
        sb.append(", userId varchar(100)");
        sb.append(", strategy varchar(255)");
        sb.append(")");

        jdbcTemplate.execute(sb.toString());
    }

    public static void createPersistentLoginsTable(JdbcOperations jdbcTemplate) throws SQLException {
        String sql = "create table persistent_logins (username varchar(64) not null";
        StringBuilder sb = new StringBuilder(sql);
        sb.append(", series varchar(64) primary key");
        sb.append(", token varchar(64) not null");
        sb.append(", last_used timestamp not null)");

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

    public static void createDB(JdbcOperations jdbcTemplate) throws SQLException {
        createEventDB(jdbcTemplate);
        createUserDB(jdbcTemplate);
        createTicketDB(jdbcTemplate);
        createAirdateDB(jdbcTemplate);
        createEventCounterDB(jdbcTemplate);
        createAuditoriumDB(jdbcTemplate);
        createDiscountCounterDB(jdbcTemplate);
        createPersistentLoginsTable(jdbcTemplate);
    }
}
