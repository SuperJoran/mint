package be.superjoran.mint.domain.searchresults

import be.superjoran.mint.domain.Category
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

class DestinationCategory : Serializable {
        @Column
        var destinationAccountNumber: String? = null;
        @ManyToOne
        @JoinColumn(name = "categoryUuid")
        var category: Category? = null

}