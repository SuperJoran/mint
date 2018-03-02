package be.superjoran.mint.services

import be.superjoran.common.model.DomainObjectCrudServiceSupport
import be.superjoran.mint.dao.StatementDao
import be.superjoran.mint.domain.Category
import be.superjoran.mint.domain.Person
import be.superjoran.mint.domain.Statement
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class StatementServiceImpl(override val dao: StatementDao) : DomainObjectCrudServiceSupport<Statement>(), StatementService {

    override fun findAllByOwner(owner: Person): List<Statement> {
        return this.dao.findAllByOriginatingAccount_Owner(owner)
    }

    override fun assignCategories(statements: List<Statement>, category: Category) {
        val list = statements.stream()
                .peek { statement -> statement.category = category }
                .collect(Collectors.toList())
        this.save(list)
    }
}
