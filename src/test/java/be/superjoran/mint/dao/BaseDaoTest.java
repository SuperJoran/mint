package be.superjoran.mint.dao;

import be.superjoran.mint.dao.config.PersistenceTestConfig;
import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceTestConfig.class})
public abstract class BaseDaoTest {

    @Autowired
    private StatementDao statementDao;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private BankAccountDao bankAccountDao;

    @Before
    public void init() {
        this.statementDao.deleteAllInBatch();
        this.bankAccountDao.deleteAllInBatch();
        this.personDao.deleteAllInBatch();
    }

    protected Person createTestUser(String name) {
        Person testPerson = new Person(name, name);

        return this.personDao.save(testPerson);
    }

    protected BankAccount createTestBankAccount(String number, Person owner) {
        BankAccount bankAccount = new BankAccount(owner, number);

        return this.bankAccountDao.save(bankAccount);
    }

}
