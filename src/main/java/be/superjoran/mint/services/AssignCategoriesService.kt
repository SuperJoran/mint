package be.superjoran.mint.services

import be.superjoran.mint.domain.Person

interface AssignCategoriesService {

    fun assignCategoriesForPerson(person: Person)
}
