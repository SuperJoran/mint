package be.superjoran.mint.services

import be.superjoran.mint.dao.DestinationCategoryDao
import be.superjoran.mint.domain.Person
import be.superjoran.mint.domain.searchresults.AutomaticallyAssignOption
import be.superjoran.mint.domain.searchresults.DestinationCategory
import org.springframework.stereotype.Service

@Service
class DestinationCategoryServiceImpl(private val dao: DestinationCategoryDao) : DestinationCategoryService {

    override fun assignInternalCategory(owner: Person) {
        this.dao.assignInternalCategory(owner.uuid!!)
    }

    override fun assignCategoriesAutomatically(owner: Person) {
        this.dao.assignCategoriesAutomatically(owner.uuid!!)
    }

    override fun findDestinationCategories(): List<DestinationCategory> {
        return this.dao.findDestinationCategories()
    }

    override fun findNumberOfStatementsThatCanBeAssigned(owner: Person): Int {
        return this.dao.findNumberOfStatementsThatCanBeAssigned(owner.uuid!!)
    }

    override fun findCategoriesThatHavePossibleCandidates(owner: Person): List<AutomaticallyAssignOption> {
        return this.dao.findCategoriesThatHavePossibleCandidates(owner.uuid!!)
    }
}
