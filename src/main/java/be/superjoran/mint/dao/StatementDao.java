package be.superjoran.mint.dao;

import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatementDao extends JpaRepository<Statement, String> {

    List<Statement> findAllByOriginatingAccount_Owner(Person owner);
}
