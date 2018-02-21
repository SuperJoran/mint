package be.superjoran.mint.services;

import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.Statement;
import be.superjoran.mint.domain.searchresults.CsvFile;
import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CsvServiceImplTest {

    @Mock
    private BankAccountService bankAccountService;
    @Mock
    private StatementService statementService;

    @Test
    public void identifyBankAccount_BELFIUS() {

        Person person = new Person("test user 2");
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setNumber("BE08 0321 0249 2813");

        when(this.bankAccountService.findAllByOwner(any()))
                .thenReturn(Collections.singletonList(bankAccount1));

        BankAccount result = this.getService().identifyBankAccount(this.getClass().getResource("BELFIUS_EXAMPLE_CSV_1.csv").getPath(), person);

        assertThat(result).isNotNull();

    }

    @Test
    public void uploadCSVFile_BELFIUS() {

        Person person = new Person("test user 2");

        when(this.bankAccountService.findAllByOwner(any()))
                .thenReturn(Collections.emptyList());
        when(this.statementService.save(any(Iterable.class)))
                .thenAnswer(i -> i.getArgument(0));

        List<CsvFile> list = this.getService().identifyCsvFiles(Collections.singletonList(new File(this.getClass().getResource("BELFIUS_EXAMPLE_CSV_1.csv").getPath())), person);

        Iterable<Statement> statements = this.getService().uploadCSVFiles(list);

        assertThat(statements).isNotEmpty()
                .hasSize(5)
                .extracting(Statement::getAmount, Statement::getDate, Statement::getDestinationAccountNumber)
                .containsExactlyInAnyOrder(
                        new Tuple(new BigDecimal("21.00"), LocalDate.of(2017,12,12), "BE72123410567316"),
                        new Tuple(new BigDecimal("47.50"), LocalDate.of(2017,12,9), "BE72123410567316"),
                        new Tuple(new BigDecimal("69.90"), LocalDate.of(2017,12,9), "BE72123410567316"),
                        new Tuple(new BigDecimal("-10.00"), LocalDate.of(2017,12,8), "BE57987417779035"),
                        new Tuple(new BigDecimal("-20.00"), LocalDate.of(2017,12,8), "BE57987417779035")
                );
    }

    private CsvService getService() {
        return new CsvServiceImpl(this.bankAccountService, statementService);
    }
}