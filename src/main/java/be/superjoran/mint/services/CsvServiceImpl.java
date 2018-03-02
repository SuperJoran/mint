package be.superjoran.mint.services;


import be.superjoran.mint.domain.Bank;
import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.Statement;
import be.superjoran.mint.domain.searchresults.CsvFile;
import com.google.common.base.CharMatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public CsvServiceImpl(BankAccountService bankAccountService, StatementService statementService) {
        this.bankAccountService = bankAccountService;
        this.statementService = statementService;
    }

    private static final Map<Bank, CsvOptions> SUPPORTED_BANKS_OPTIONS = new EnumMap<>(Bank.class);

    static {
        SUPPORTED_BANKS_OPTIONS.put(Bank.BELFIUS, new CsvOptions(";", s -> s.startsWith("BE"), "dd/MM/yyyy", 10, 1, 0, 4, 8));
        SUPPORTED_BANKS_OPTIONS.put(Bank.ING, new CsvOptions(";", s -> Character.isDigit(s.charAt(0)), "dd/MM/yyyy", 6, 4, 0, 2, 8));
        SUPPORTED_BANKS_OPTIONS.put(Bank.KEYTRADE, new CsvOptions(";", s -> false, "dd.MM.yyyy", 5, 1, -1, 3, 4));
    }

    @Override
    public BankAccount identifyBankAccount(String fileUrl, @NotNull Person person) {
        Map<String, BankAccount> bankAccountMap = this.bankAccountService.findAllByOwner(person)
                .stream()
                .collect(Collectors.toMap(BankAccount::getNumber, b -> b));
        BankAccount bankAccount = null;

        try (BufferedReader br = new BufferedReader(new FileReader(fileUrl))) {
            String line;

            while ((line = br.readLine()) != null && bankAccount == null) {
                for (Entry<Bank, CsvOptions> entry : SUPPORTED_BANKS_OPTIONS.entrySet()) {
                    String[] row = line.split(entry.getValue().getCvsSplitBy());
                    if (row.length > 0 && entry.getValue().getIdentifyStatementPredicate().test(line)) {
                        bankAccount = bankAccountMap.get(row[entry.getValue().getRowNumberFromAccount()]);
                        if (bankAccount == null) {
                            bankAccount = new BankAccount(person, entry.getKey(), row[entry.getValue().getRowNumberFromAccount()]);
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
    public Iterable<Statement> uploadCSVFiles(@NotNull @Valid List<CsvFile> files) {
        return this.statementService.save(files.stream()
                .flatMap(f -> this.uploadCsv(f, SUPPORTED_BANKS_OPTIONS.get(f.getBankAccount().getBank())).stream())
                .collect(Collectors.toList()));
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
                        String toAccount = StringUtils.replace(this.getValueFromStringArrayAtPosition(row, options.getRowNumberToAccount()), " ", "");

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
        return CharMatcher.whitespace().trimFrom(stringArray[position]);
    }
}
