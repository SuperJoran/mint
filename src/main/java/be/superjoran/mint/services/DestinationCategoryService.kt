package be.superjoran.mint.services

import be.superjoran.mint.domain.Person
import be.superjoran.mint.domain.searchresults.DestinationCategory

interface DestinationCategoryService {
    fun findDestinationCategories(): List<DestinationCategory>

    fun assignCategoriesAutomatically(owner: Person)

    fun findNumberOfStatementsThatCanBeAssigned(owner: Person): Int

    fun assignInternalCategory(owner: Person)
}
