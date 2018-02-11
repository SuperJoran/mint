package be.superjoran.mint.services;

import be.superjoran.common.model.DomainObjectCrudServiceSupport;
import be.superjoran.mint.dao.StatementDao;
import be.superjoran.mint.domain.Statement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

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
}
