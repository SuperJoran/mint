package be.superjoran.mint.services

import be.superjoran.common.model.DomainObjectCrudServiceSupport
import be.superjoran.mint.dao.BankAccountDao
import be.superjoran.mint.domain.Bank
import be.superjoran.mint.domain.BankAccount
import be.superjoran.mint.domain.Person
import be.superjoran.mint.domain.searchresults.BankAccountCandidate
import org.springframework.stereotype.Service
import java.util.Objects.requireNonNull
import javax.validation.Validator

@Service
class BankAccountServiceImpl(
        override val dao: BankAccountDao,
        override val validator: Validator
) : DomainObjectCrudServiceSupport<BankAccount>(), BankAccountService {

    override fun findAllByOwner(administrator: Person): List<BankAccount> {
        return this.dao.findAllByOwner(administrator)
    }

    override fun create(bankAccountCandidate: BankAccountCandidate): BankAccount {
        return this.save(BankAccount(bankAccountCandidate.owner, requireNonNull<Bank>(bankAccountCandidate.bank), requireNonNull<String>(bankAccountCandidate.number), bankAccountCandidate.name))
    }
}
