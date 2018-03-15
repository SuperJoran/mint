package be.superjoran.mint.domain.searchresults

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

class DestinationCategory : Serializable {
    @Column(name = "destinationaccount_number")
        var destinationAccountNumber: String? = null;
        @ManyToOne
        @JoinColumn(name = "category_uuid")
        var categoryUuid: String? = null

}