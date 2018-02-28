package be.superjoran.mint.services

import be.superjoran.mint.dao.DestinationCategoryDao
import be.superjoran.mint.domain.searchresults.DestinationCategory
import org.springframework.stereotype.Service

@Service
class DestinationCategoryServiceImpl(private val dao: DestinationCategoryDao) : DestinationCategoryService {

    override fun findDestinationCategories(): List<DestinationCategory> {
        return this.dao.findDestinationCategories()
    }
}
