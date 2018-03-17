package be.superjoran.mint.domain.searchresults

import be.superjoran.mint.domain.Category
import java.io.Serializable
import java.math.BigDecimal

class StatementSearchCriteria : Serializable {
    var fuzzySearch: String? = null
    var filterByCategory = false
    var category: Category? = null
    var minimum: BigDecimal? = null
    var maximum: BigDecimal? = null
}
