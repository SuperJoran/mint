package be.superjoran.mint.services

import be.superjoran.common.model.DomainObjectCrudService
import be.superjoran.mint.domain.Person
import be.superjoran.mint.domain.searchresults.PersonCandidate

interface PersonService : DomainObjectCrudService<Person> {
    fun logIn(person: PersonCandidate): Person

    fun create(personCandidate: PersonCandidate): Person
}
