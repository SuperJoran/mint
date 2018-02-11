package be.superjoran.mint.services;

import be.superjoran.common.model.DomainObjectCrudServiceSupport;
import be.superjoran.mint.dao.BankAccountDao;
import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountServiceImpl extends DomainObjectCrudServiceSupport<BankAccount> implements BankAccountService {
    private final BankAccountDao bankAccountDao;

    public BankAccountServiceImpl(BankAccountDao bankAccountDao) {
        this.bankAccountDao = bankAccountDao;
    }

    @Override
    protected CrudRepository<BankAccount, String> getDao() {
        return this.bankAccountDao;
    }

    @Override
    public List<BankAccount> findByAdministrator(Person administrator) {
        return this.bankAccountDao.findAllByAdministrator(administrator);
    }
}
