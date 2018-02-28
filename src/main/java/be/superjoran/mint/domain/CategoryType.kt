package be.superjoran.mint.domain

/**
 * Created by Jorandeboever on 5/7/2017.
 */
enum class CategoryType : Display {
    INCOME, EXPENSE, UNTRACKED;

    override val id: String?
        get() = this.name

    override val displayValue: String?
        get() = this.name
}
