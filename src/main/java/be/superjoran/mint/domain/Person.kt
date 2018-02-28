package be.superjoran.mint.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * Created by Ghostwritertje
 * Date: 29-Sep-16.
 */
@Table(name = "T_PERSON")
@Entity
class Person(
        @Column(unique = true, nullable = false)
        @NotNull
        @NotBlank
        var username: String,
        @Column(nullable = false, name = "password")
        @NotNull
        @NotBlank
        var password: String
) : DomainObject() {

    override fun toString(): String {
        return String.format("Person with name '%s'", this.username)
    }
}
