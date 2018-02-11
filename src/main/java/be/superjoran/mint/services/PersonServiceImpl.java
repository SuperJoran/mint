package be.superjoran.mint.services;

import be.superjoran.common.model.DomainObjectCrudServiceSupport;
import be.superjoran.mint.dao.PersonDao;
import be.superjoran.mint.domain.Person;
import be.superjoran.utilities.PasswordUtility;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

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
    public Person save(Person object) {
        object.setPassword(PasswordUtility.hashPassword(object.getPassword()));
        return super.save(object);
    }

    @Override
    public Person logIn(Person person) {
        return this.personDao.findByUsernameAndPassword(person.getUsername(), PasswordUtility.hashPassword(person.getPassword()));
    }
}
