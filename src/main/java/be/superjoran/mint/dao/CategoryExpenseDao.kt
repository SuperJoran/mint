package be.superjoran.mint.dao

import be.superjoran.mint.domain.searchresults.CategoryExpense

interface CategoryExpenseDao {

    fun findCategoryExpensesPerYearByOwner(ownerUuid: String): List<CategoryExpense>
}
