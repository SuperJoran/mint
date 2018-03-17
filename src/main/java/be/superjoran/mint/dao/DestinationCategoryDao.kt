package be.superjoran.mint.dao

import be.superjoran.mint.domain.searchresults.DestinationCategory

interface DestinationCategoryDao {
    fun findDestinationCategories(): List<DestinationCategory>

    fun assignCategoriesAutomatically(personUuid: String)

    fun findNumberOfStatementsThatCanBeAssigned(personUuid: String): Int

    fun assignInternalCategory(ownerUuid: String)
}
