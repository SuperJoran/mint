package be.superjoran.mint.domain

import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*

/**
 * Created by Jorandeboever
 * Date: 01-Oct-16.
 */
@Entity
@Table(name = "T_STATEMENT")
class Statement(
        @ManyToOne(cascade = [(CascadeType.MERGE), (CascadeType.REFRESH)])
        @JoinColumn(name = "ORIGINATINGACCOUNT_UUID")
        var originatingAccount: BankAccount,

        @Column(name = "DESTINATIONACCOUNT_NUMBER")
        var destinationAccountNumber: String,

        var amount: BigDecimal,

        var date: LocalDate
) : DomainObject() {

    @ManyToOne(cascade = [(CascadeType.MERGE)])
    @JoinColumn(name = "CATEGORY_UUID")
    var category: Category? = null

    var description: String? = null

    @Column(name = "CSV_LINE")
    var csvLine: String? = null

}
