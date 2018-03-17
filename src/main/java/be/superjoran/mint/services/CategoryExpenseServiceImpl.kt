package be.superjoran.mint.services

import be.superjoran.mint.dao.CategoryExpenseDao
import be.superjoran.mint.domain.Person
import be.superjoran.mint.domain.searchresults.CategoryExpense
import org.springframework.stereotype.Service

@Service
class CategoryExpenseServiceImpl(private val categoryExpenseDao: CategoryExpenseDao) : CategoryExpenseService {
    override fun findCategoryExpensesPerYearByOwner(owner: Person): List<CategoryExpense> {
        return this.categoryExpenseDao.findCategoryExpensesPerYearByOwner(owner.uuid!!)
    }
}