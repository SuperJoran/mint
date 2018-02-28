package be.superjoran.mint.services

import be.superjoran.mint.domain.searchresults.DestinationCategory

interface DestinationCategoryService {
    fun findDestinationCategories(): List<DestinationCategory>

}
