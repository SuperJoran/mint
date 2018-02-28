package be.superjoran.mint.services

import be.superjoran.common.model.DomainObjectCrudServiceSupport
import be.superjoran.mint.dao.PersonDao
import be.superjoran.mint.domain.Person
import be.superjoran.mint.domain.searchresults.PersonCandidate
import be.superjoran.utilities.PasswordUtility
import org.springframework.stereotype.Service
import java.util.Objects.requireNonNull

@Service
class PersonServiceImpl(override val dao: PersonDao) : DomainObjectCrudServiceSupport<Person>(), PersonService {


    override fun create(`object`: PersonCandidate): Person {
        requireNonNull<String>(`object`.username)
        return super.save(Person(`object`.username!!, PasswordUtility.hashPassword(`object`.password)!!))
    }

    override fun logIn(person: PersonCandidate): Person {
        return this.dao.findByUsernameAndPassword(person.username!!, PasswordUtility.hashPassword(person.password)!!)
    }
}
