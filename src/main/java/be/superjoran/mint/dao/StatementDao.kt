package be.superjoran.mint.dao

import be.superjoran.mint.domain.Person
import be.superjoran.mint.domain.Statement
import org.springframework.data.jpa.repository.JpaRepository

interface StatementDao : JpaRepository<Statement, String> {

    fun findAllByOriginatingAccount_Owner(owner: Person): List<Statement>
}
