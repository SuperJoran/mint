package be.superjoran.mint.services;

import be.superjoran.common.model.DomainObjectCrudService;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.searchresults.PersonCandidate;

public interface PersonService extends DomainObjectCrudService<Person> {
    Person logIn(PersonCandidate person);

    Person create(PersonCandidate personCandidate);
}
