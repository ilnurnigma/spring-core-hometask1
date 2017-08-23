package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.DomainObject;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DomainObjectDAO<T extends DomainObject> {
    protected Collection<T> domainObjects = new HashSet<>();

    public boolean save(T object) {
        return domainObjects.add(object);
    }

    public boolean remove(T object) {
        return domainObjects.remove(object);
    }

    /**
     * Getting object by id from storage
     *
     * @param id id of the object
     * @return Found object or <code>null</code>
     */
    public T getById(@Nonnull Long id) {
        for (T object : domainObjects) {
            if (id.equals(object.getId())) {
                return object;
            }
        }

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
}
