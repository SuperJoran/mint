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
class Statement : DomainObject() {

    @ManyToOne(cascade = arrayOf(CascadeType.MERGE, CascadeType.PERSIST))
    @JoinColumn(name = "ORIGINATINGACCOUNT_UUID")
    var originatingAccount: BankAccount? = null

    @Column(name = "DESTINATIONACCOUNT_NUMBER")
    var destinationAccountNumber: String? = null

    var date: LocalDate? = null
    var amount: BigDecimal? = null
        get() {
            if (field == null) {
                this.amount = BigDecimal.ZERO
            }
            return field
        }

    @ManyToOne(cascade = arrayOf(CascadeType.MERGE))
    @JoinColumn(name = "CATEGORY_UUID")
    var category: Category? = null

    var description: String? = null

    @Column(name = "CSV_LINE")
    var csvLine: String? = null

}
