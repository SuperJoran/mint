package be.superjoran.mint.services;


import be.superjoran.mint.domain.Bank;
import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.Statement;
import be.superjoran.mint.domain.searchresults.CsvFile;
import com.google.common.base.CharMatcher;
import kotlin.text.Regex;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by jorandeboever
 * on 18/03/16.
 */
@Service
public class CsvServiceImpl implements CsvService {
    private static final Pattern PATTERN = Pattern.compile(",", Pattern.LITERAL);

    private static final Logger LOG = LogManager.getLogger();

    private final BankAccountService bankAccountService;
    private final StatementService statementService;
    private final DestinationCategoryService destinationCategoryService;

    public CsvServiceImpl(BankAccountService bankAccountService, StatementService statementService, DestinationCategoryService destinationCategoryService) {
        this.bankAccountService = bankAccountService;
        this.statementService = statementService;
        this.destinationCategoryService = destinationCategoryService;
    }

    private static final Map<Bank, CsvOptions> SUPPORTED_BANKS_OPTIONS = new EnumMap<>(Bank.class);

    static {
        SUPPORTED_BANKS_OPTIONS.put(Bank.KEYTRADE, new CsvOptions(";", s -> {
            Regex regex = new Regex("^([0-9]{4})\\/([0-9]{4})\\/([0-9]{4})+$");
            return regex.matches(StringUtils.strip(s.split(";")[0], "�"));
        }, "dd.MM.yyyy", 5, 1, null, 3, 4));
        SUPPORTED_BANKS_OPTIONS.put(Bank.BELFIUS, new CsvOptions(";", s -> s.startsWith("BE"), "dd/MM/yyyy", 10, 1, 0, 4, 8));
        SUPPORTED_BANKS_OPTIONS.put(Bank.ING, new CsvOptions(";", s -> Character.isDigit(s.charAt(0)), "dd/MM/yyyy", 6, 4, 0, 2, 8));
    }

    @Override
    public BankAccount identifyBankAccount(String fileUrl, @NotNull Person person) {
        Map<String, BankAccount> bankAccountMap = this.bankAccountService.findAllByOwner(person)
                .stream()
                .collect(Collectors.toMap(BankAccount::getNumber, b -> b));
        BankAccount bankAccount = null;

        try (BufferedReader br = new BufferedReader(new FileReader(fileUrl))) {
            String line;

            boolean bankFound = false;
            while ((line = br.readLine()) != null && !bankFound) {
                for (Entry<Bank, CsvOptions> entry : SUPPORTED_BANKS_OPTIONS.entrySet()) {
                    String[] row = line.split(entry.getValue().getCvsSplitBy());
                    if (row.length > 0 && entry.getValue().getIdentifyStatementPredicate().test(line)) {
                        bankFound = true;
                        Integer rowNumberFromAccount = entry.getValue().getRowNumberFromAccount();
                        if (rowNumberFromAccount != null) {
                            bankAccount = bankAccountMap.get(row[rowNumberFromAccount]);
                            if (bankAccount == null) {
                                BankAccount newBankAccount = new BankAccount(person, entry.getKey(), row[entry.getValue().getRowNumberFromAccount()]);
                                bankAccountMap.put(newBankAccount.getNumber(), newBankAccount);
                                bankAccount = newBankAccount;
                            }
                        }

                    }
                }
            }

        } catch (IOException e) {
            LOG.error(() -> String.format("Exception (%s) caught in identifyBankAccount2: %s", e.getClass().getName(), e.getMessage()), e);
        }
        return bankAccount;
    }

    @NotNull
    @Override
    public List<CsvFile> identifyCsvFiles(@NotNull List<? extends File> files, @NotNull Person person) {
        return files.stream().map(fileUrl -> new CsvFile(fileUrl, this.identifyBankAccount(fileUrl.getAbsolutePath(), person))).collect(Collectors.toList());
    }

    @NotNull
    @Override
    public Iterable<Statement> uploadCSVFiles(@NotNull @Valid List<CsvFile> files, @NotNull Person person) {
        this.persistBankAccounts(files, person);

        Iterable<Statement> result = this.statementService.save(files.stream()
                .flatMap(f -> this.uploadCsv(f, SUPPORTED_BANKS_OPTIONS.get(f.getBankAccount().getBank())).stream())
                .collect(Collectors.toList()));

        this.destinationCategoryService.assignInternalCategory(person);
        return result;
    }

    private void persistBankAccounts(@Valid @NotNull List<CsvFile> files, @NotNull Person person) {
        List<BankAccount> uniqueBankAccounts = io.vavr.collection.List.ofAll(files)
                .filter(csvFile -> csvFile.getBankAccount() != null)
                .map(CsvFile::getBankAccount)
                .distinctBy(BankAccount::getNumber)
                .filter(bankAccount -> bankAccount.getUuid() == null)
                .toJavaList();

        this.bankAccountService.save(uniqueBankAccounts);

        Map<String, BankAccount> updatedBankAccounts = this.bankAccountService.findAllByOwner(person).stream().collect(Collectors.toMap(BankAccount::getNumber, Function.identity()));

        files.forEach(f -> f.setBankAccount(updatedBankAccounts.get(f.getBankAccount().getNumber())));
    }


    private Collection<Statement> uploadCsv(@Valid CsvFile csvFile, CsvOptions options) {
        String line;

        Collection<Statement> statementList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile.getFileUrl()))) {
            while ((line = br.readLine()) != null) {

                String[] row = line.split(options.getCvsSplitBy());

                if (row.length > 0 && options.getIdentifyStatementPredicate().test(line)) {
                    String bedragString = this.getValueFromStringArrayAtPosition(row, options.getRowNumberAmount());
                    if (!StringUtils.isEmpty(bedragString)) {

                        BigDecimal amount = new BigDecimal(PATTERN.matcher(bedragString).replaceAll(Matcher.quoteReplacement(".")));
                        LocalDate date = LocalDate.parse(this.getValueFromStringArrayAtPosition(row, options.getRowNumberDate()), DateTimeFormatter.ofPattern(options.getDatePattern()));
                        String toAccount = this.getValueFromStringArrayAtPosition(row, options.getRowNumberToAccount());

                        Statement statement = new Statement(csvFile.getBankAccount(), toAccount, amount, date);

                        statement.setDescription(this.getValueFromStringArrayAtPosition(row, options.getRowNumberDescription()));
                        statement.setOriginatingAccount(csvFile.getBankAccount());
                        statement.setDestinationAccountNumber(toAccount);
                        statement.setCsvLine(line);

                        statementList.add(statement);
                    }

                }
            }

            return statementList;
        } catch (IOException e) {
            LOG.error(() -> String.format("Exception (%s) caught in uploadCsv: %s", e.getClass().getName(), e.getMessage()), e);
        }

        return Collections.emptyList();
    }

    private String getValueFromStringArrayAtPosition(String[] stringArray, int position) {
        //Trims ALL WHITESPACE (e.g.: also specials characters as found in KEYTRADE-csv files)
        String result = CharMatcher.whitespace().trimFrom(stringArray[position]);
        if ("-".equals(result)) {
            result = "";
        }
        return result;
    }
}
