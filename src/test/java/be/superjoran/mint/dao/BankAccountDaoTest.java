package be.superjoran.mint.dao;

import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class BankAccountDaoTest extends BaseDaoTest {

    @Autowired
    private BankAccountDao bankAccountDao;


    @Test
    public void create() {
        String testPerson1Name = "test person 1";
        Person testPerson1 = this.createTestUser(testPerson1Name);

        String bankAccountNumber = "IBAN 1245 1515 5874";
        BankAccount bankAccount = new BankAccount(testPerson1, bankAccountNumber);

        this.bankAccountDao.save(bankAccount);

        List<BankAccount> bankAccounts = this.bankAccountDao.findAll();

        assertThat(bankAccounts).isNotEmpty()
                .hasSize(1)
                .extracting(BankAccount::getNumber, ba -> ba.getOwner().getUsername())
                .containsExactlyInAnyOrder(new Tuple(bankAccountNumber, testPerson1Name));
    }
}
