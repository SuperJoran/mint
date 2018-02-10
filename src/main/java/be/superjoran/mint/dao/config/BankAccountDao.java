package be.superjoran.mint.dao.config;

import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountDao extends JpaRepository<BankAccount, String> {

    List<BankAccount> findAllByAdministrator(Person administrator);
}
