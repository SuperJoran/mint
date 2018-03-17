package be.superjoran.mint.services

import be.superjoran.common.model.DomainObjectCrudService
import be.superjoran.mint.domain.BankAccount
import be.superjoran.mint.domain.Person
import be.superjoran.mint.domain.searchresults.BankAccountCandidate

interface BankAccountService : DomainObjectCrudService<BankAccount> {

    fun findAllByOwner(administrator: Person): List<BankAccount>

    fun create(bankAccountCandidate: BankAccountCandidate): BankAccount
}
