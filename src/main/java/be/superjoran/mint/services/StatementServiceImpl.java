package be.superjoran.mint.services;

import be.superjoran.common.model.DomainObjectCrudServiceSupport;
import be.superjoran.mint.dao.StatementDao;
import be.superjoran.mint.domain.Category;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.Statement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatementServiceImpl extends DomainObjectCrudServiceSupport<Statement> implements StatementService {

    private final StatementDao statementDao;

    public StatementServiceImpl(StatementDao statementDao) {
        this.statementDao = statementDao;
    }

    @Override
    protected CrudRepository<Statement, String> getDao() {
        return this.statementDao;
    }

    @Override
    public List<Statement> findAllByOwner(Person owner) {
        return this.statementDao.findAllByOriginatingAccount_Owner(owner);
    }

    @Override
    public void assignCategories(List<Statement> statements, Category category) {
        this.save(statements.stream()
                .peek(statement -> statement.setCategory(category))
                .collect(Collectors.toList()));
    }
}
