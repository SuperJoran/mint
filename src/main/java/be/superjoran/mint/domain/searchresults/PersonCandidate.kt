package be.superjoran.mint.domain.searchresults

import java.io.Serializable
import javax.validation.constraints.NotEmpty

class PersonCandidate : Serializable {
    @NotEmpty
    var username: String? = null
    @NotEmpty
    var password: String? = null
}
