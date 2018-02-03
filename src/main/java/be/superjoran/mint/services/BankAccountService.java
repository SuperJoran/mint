package be.superjoran.mint.services;

import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;

import java.util.List;

public interface BankAccountService {

    List<BankAccount> findByAdministrator(Person administrator);
}
