package be.superjoran.common.model;


import be.superjoran.mint.domain.DomainObject;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jorandeboever
 * Date: 01-Oct-16.
 */
public interface DomainObjectCrudService<T extends DomainObject> {
    Optional<T> findOne(String id);

    void delete(T object);

    T save(T object);

    List<T> findAll();

    Iterable<T> save(Iterable<T> objects);

}
