package be.superjoran.mint.services;

import be.superjoran.common.model.DomainObjectCrudServiceSupport;
import be.superjoran.mint.dao.BankAccountDao;
import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.searchresults.BankAccountCandidate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNull;

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
    public List<BankAccount> findAllByOwner(Person administrator) {
        return this.bankAccountDao.findAllByOwner(administrator);
    }

    @Override
    public BankAccount createOrUpdate(BankAccountCandidate bankAccountCandidate) {
        return this.save(new BankAccount(bankAccountCandidate.getOwner(), requireNonNull(bankAccountCandidate.getBank()), requireNonNull(bankAccountCandidate.getNumber()), bankAccountCandidate.getName()));
    }
}
