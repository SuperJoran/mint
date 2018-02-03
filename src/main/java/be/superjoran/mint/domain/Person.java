package be.superjoran.mint.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Ghostwritertje
 * Date: 29-Sep-16.
 */
@Table(name = "T_PERSON")
@Entity
public class Person extends DomainObject {
    private static final long serialVersionUID = 5264397082676105919L;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false, name = "password")
    private String password;

    public Person() {
    }

    public Person(String username) {
        this.username = username;
    }

    public String getUsername() {
        if (this.username == null) {
            this.username = "";
        }
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("Person with name '%s'", this.username);
    }
}
