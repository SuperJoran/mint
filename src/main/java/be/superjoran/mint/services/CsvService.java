package be.superjoran.mint.services;

import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.Statement;
import be.superjoran.mint.domain.searchresults.CsvFile;

import java.io.File;
import java.util.List;

public interface CsvService {

    Iterable<Statement> uploadCSVFiles(List<CsvFile> file);

    BankAccount identifyBankAccount(String fileUrl, Person person);

    List<CsvFile> identifyCsvFiles(List<File> files, Person person);
}
