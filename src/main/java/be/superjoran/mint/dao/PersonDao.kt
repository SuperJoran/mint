package be.superjoran.mint.dao

import be.superjoran.mint.domain.Person
import org.springframework.data.jpa.repository.JpaRepository

interface PersonDao : JpaRepository<Person, String> {
    fun findByUsernameAndPassword(username: String, password: String): Person
}
