package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.domain.DomainObject;
import ua.epam.spring.hometask.service.AbstractDomainObjectService;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashSet;

public class DomainObjectServiceImpl<T extends DomainObject> implements AbstractDomainObjectService<T> {
    protected Collection<T> domainObjects = new HashSet<>();

    @Override
    public T save(@Nonnull T object) {
        if (domainObjects.add(object)) {
            return object;
        }

        return null;
    }

    @Override
    public void remove(@Nonnull T object) {
        domainObjects.remove(object);
    }

    @Override
    public T getById(@Nonnull Long id) {
        for (T domainObject : domainObjects) {
            if (id.equals(domainObject.getId())) {
                return domainObject;
            }
        }

        return null;
    }

    @Nonnull
    @Override
    public Collection<T> getAll() {
        return domainObjects;
    }
}
