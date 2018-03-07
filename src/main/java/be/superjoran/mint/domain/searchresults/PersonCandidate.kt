package be.superjoran.mint.domain.searchresults

import java.io.Serializable
import javax.validation.constraints.NotEmpty

class PersonCandidate : Serializable {
    @get:NotEmpty
    var username: String? = null
    @get:NotEmpty
    var password: String? = null
}
