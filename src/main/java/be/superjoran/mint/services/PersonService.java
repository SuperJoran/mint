package be.superjoran.mint.services;

import be.superjoran.common.model.DomainObjectCrudService;
import be.superjoran.mint.domain.Person;

public interface PersonService extends DomainObjectCrudService<Person> {
    Person logIn(Person person);
}
