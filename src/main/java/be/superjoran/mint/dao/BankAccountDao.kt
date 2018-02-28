package be.superjoran.mint.dao

import be.superjoran.mint.domain.BankAccount
import be.superjoran.mint.domain.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BankAccountDao : JpaRepository<BankAccount, String> {

    fun findAllByOwner(administrator: Person): List<BankAccount>
}
