package be.superjoran.mint.domain

/**
 * Created by Jorandeboever on 5/7/2017.
 */
enum class CategoryType : Display {
    INCOME, EXPENSE, UNTRACKED;

    override fun getId(): String {
        return this.name
    }

    override fun getDisplayValue(): String {
        return this.name
    }
}
