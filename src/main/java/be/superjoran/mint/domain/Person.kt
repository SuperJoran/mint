package be.superjoran.mint.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

/**
 * Created by Ghostwritertje
 * Date: 29-Sep-16.
 */
@Table(name = "T_PERSON")
@Entity
class Person : DomainObject {

    @Column(unique = true, nullable = false)
    var username: String? = null

    @Column(nullable = false, name = "password")
    var password: String? = null

    constructor() {}

    constructor(username: String) {
        this.username = username
    }

    override fun toString(): String {
        return String.format("Person with name '%s'", this.username)
    }
}
