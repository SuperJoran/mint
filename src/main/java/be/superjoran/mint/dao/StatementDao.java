package be.superjoran.mint.dao;

import be.superjoran.mint.domain.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatementDao extends JpaRepository<Statement, String> {
}
