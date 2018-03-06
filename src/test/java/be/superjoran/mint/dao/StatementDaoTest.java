package be.superjoran.mint.dao;

import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.Statement;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class StatementDaoTest extends BaseDaoTest {

    @Autowired
    private StatementDao statementDao;

    @Test
    public void create() {
        Person testPerson1 = this.createTestUser("test person 1");

        BankAccount bankAccount1 = this.createTestBankAccount("IBAN 9292 2283 2828", testPerson1);

        Statement statement1 = new Statement(bankAccount1, "IBAN 8238 2883 2883", new BigDecimal("50.00"), LocalDate.of(2018, 2, 1));

        Statement result = this.statementDao.save(statement1);

        assertThat(result)
                .isNotNull();
    }
}
