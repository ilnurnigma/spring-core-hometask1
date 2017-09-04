package ua.epam.spring.hometask.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.epam.spring.hometask.domain.DomainObject;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

public abstract class DomainObjectDAO<T extends DomainObject> {
    protected Collection<T> domainObjects = new HashSet<>();
    protected JdbcTemplate jdbcTemplate;
    protected String tableName;

    public DomainObject save(T object) {
        jdbcTemplate.update("insert into " + tableName + " (id, msg) values (?, ?)", object.getId(), "some message");
        domainObjects.add(object);
        return object;
    }

    public boolean remove(T object) {
        jdbcTemplate.update("delete from " + tableName + " where id=?", object.getId());
        return domainObjects.remove(object);
    }

    /**
     * Getting object by id from storage
     *
     * @param id id of the object
     * @return Found object or <code>null</code>
     */
    public T getById(@Nonnull Long id) {
        jdbcTemplate.queryForObject("select id, msg from " + tableName + " where id=?", new RowMapper<T>() {
            @Override
            public T mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
                DomainObject domainObject = new DomainObject();
                domainObject.setId(resultSet.getLong("id"));

                return (T) domainObject;
            }
        }, id);

        return null;
    }

    /**
     * Getting all objects from storage
     *
     * @return collection of objects
     */
    @Nonnull
    public Collection<T> getAll() {
        Collection<T> objects = new HashSet<>();
        objects.addAll(domainObjects);

        return objects;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
