package be.superjoran.mint.services;

import be.superjoran.common.model.DomainObjectCrudServiceSupport;
import be.superjoran.mint.dao.PersonDao;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.searchresults.PersonCandidate;
import be.superjoran.utilities.PasswordUtility;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class PersonServiceImpl extends DomainObjectCrudServiceSupport<Person> implements PersonService {
    private final PersonDao personDao;

    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    protected CrudRepository<Person, String> getDao() {
        return this.personDao;
    }

    @Override
    public Person create(PersonCandidate object) {
        requireNonNull(object.getUsername());
        return super.save(new Person(object.getUsername(), PasswordUtility.hashPassword(object.getPassword())));
    }

    @Override
    public Person logIn(PersonCandidate person) {
        return this.personDao.findByUsernameAndPassword(person.getUsername(), PasswordUtility.hashPassword(person.getPassword()));
    }
}
