package be.superjoran.mint.services;

import be.superjoran.common.model.DomainObjectCrudService;
import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;

import java.util.List;

public interface BankAccountService extends DomainObjectCrudService<BankAccount> {

    List<BankAccount> findByAdministrator(Person administrator);
}
