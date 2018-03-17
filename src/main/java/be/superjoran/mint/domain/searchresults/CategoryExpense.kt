package be.superjoran.mint.domain.searchresults

import java.io.Serializable
import java.math.BigDecimal

class CategoryExpense : Serializable {
    var category: String? = ""
    var sum: BigDecimal? = BigDecimal.ZERO
    var year: String? = ""
}
