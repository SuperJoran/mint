package be.superjoran.mint.dao;

import be.superjoran.mint.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDao extends JpaRepository<Person, String> {
    Person findByUsernameAndPassword(String username, String password);
}
