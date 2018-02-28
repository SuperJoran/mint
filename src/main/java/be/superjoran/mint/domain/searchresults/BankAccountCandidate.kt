package be.superjoran.mint.domain.searchresults

import be.superjoran.mint.domain.Bank
import be.superjoran.mint.domain.BankAccount
import be.superjoran.mint.domain.Person
import java.io.Serializable
import javax.validation.constraints.NotNull

class BankAccountCandidate (
        var owner: Person
) : Serializable {
    var name: String? = null

    @NotNull
    var number: String? = null

    @NotNull
    var bank: Bank? = null

    constructor(bankAccount: BankAccount) : this(bankAccount.owner) {
        this.number = bankAccount.number
        this.name = bankAccount.name
    }
}

