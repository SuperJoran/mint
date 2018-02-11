package be.superjoran.mint.domain

import org.hibernate.annotations.Formula
import java.math.BigDecimal
import javax.persistence.*

/**
 * Created by Jorandeboever
 * Date: 01-Oct-16.
 */
@Entity
@Table(name = "T_BANKACCOUNT")
class BankAccount : DomainObject {

    @ManyToOne
    @JoinColumn(name = "OWNER_UUID")
    var owner: Person? = null

    var bank: Bank? = null

    @Column(nullable = false)
    var number: String? = null

    @Column
    var name: String? = null

    @Formula(value = "(SELECT SUM(statement.AMOUNT) FROM T_STATEMENT statement WHERE statement.ORIGINATINGACCOUNT_UUID = UUID)")
    @Access(AccessType.FIELD)
    var balance: BigDecimal? = null

    constructor() {}

    constructor(owner: Person) {
        this.owner = owner
    }

    constructor(owner: Person, bank: Bank, number: String) {
        this.owner = owner
        this.bank = bank
        this.number = number
    }

    override fun getDisplayValue(): String? {
        return this.toString()
    }

    override fun toString(): String {
        return if (this.name != null) this.name.toString() else this.number.toString()
    }
}
