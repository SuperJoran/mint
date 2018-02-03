package be.superjoran.mint.services;

import be.superjoran.mint.domain.Bank;
import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.Statement;

import java.util.Collection;
import java.util.List;

public interface CsvService {

    Collection<Statement> uploadCSVFile(String fileUrl, Person person);

    Bank identifyBankAccount(String fileUrl, Person person);
}
