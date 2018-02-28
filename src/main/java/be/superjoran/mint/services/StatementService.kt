package be.superjoran.mint.services

import be.superjoran.common.model.DomainObjectCrudService
import be.superjoran.mint.domain.Category
import be.superjoran.mint.domain.Person
import be.superjoran.mint.domain.Statement

interface StatementService : DomainObjectCrudService<Statement> {

    fun findAllByOwner(owner: Person): List<Statement>

    fun assignCategories(statements: List<Statement>, category: Category)
}
