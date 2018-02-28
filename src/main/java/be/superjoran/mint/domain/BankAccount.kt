package be.superjoran.mint.domain

import org.hibernate.annotations.Formula
import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.NotNull

/**
 * Created by Jorandeboever
 * Date: 01-Oct-16.
 */
@Entity
@Table(name = "T_BANKACCOUNT")
class BankAccount
(
        @ManyToOne
        @JoinColumn(name = "OWNER_UUID")
        var owner: Person,

        @Column(nullable = false)
        @NotNull
        var number: String
): DomainObject() {

    var bank: Bank = Bank.BELFIUS

    @Column
    var name: String? = null

    @Formula(value = "(SELECT SUM(statement.AMOUNT) FROM T_STATEMENT statement WHERE statement.ORIGINATINGACCOUNT_UUID = UUID)")
    @Access(AccessType.FIELD)
    var balance: BigDecimal? = null

    constructor(owner: Person, bank: Bank, number: String) : this(owner, number) {
        this.bank = bank
    }

    constructor(owner: Person, bank: Bank, number: String, name: String?) : this(owner, bank, number) {
        this.name = name
    }

    override val displayValue: String?
        get() = this.name

    override fun toString(): String {
        return if (this.name != null) this.name.toString() else this.number
    }
}
