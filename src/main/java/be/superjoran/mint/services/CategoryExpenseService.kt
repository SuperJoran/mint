package be.superjoran.mint.services

import be.superjoran.mint.domain.Person
import be.superjoran.mint.domain.searchresults.CategoryExpense

interface CategoryExpenseService {

    fun findCategoryExpensesPerYearByOwner(owner: Person): List<CategoryExpense>
    fun findCategoryExpensesPerMonthByOwner(owner: Person): List<CategoryExpense>

}
