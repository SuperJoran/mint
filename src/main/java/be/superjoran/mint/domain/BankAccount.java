package be.superjoran.mint.domain;

import org.hibernate.annotations.Formula;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by Jorandeboever
 * Date: 01-Oct-16.
 */
@Entity
@Table(name = "T_BANKACCOUNT")
public class BankAccount extends DomainObject {
    private static final long serialVersionUID = -4411985824034001947L;

    @ManyToOne
    @JoinColumn(name = "OWNER_UUID")
    private Person owner;

    private Bank bank;

    @Column(nullable = false)
    private String number;

    @Column
    private String name;

    @Formula(value = "(SELECT SUM(statement.AMOUNT) FROM T_STATEMENT statement WHERE statement.ORIGINATINGACCOUNT_UUID = UUID)")
    @Access(AccessType.FIELD)
    private BigDecimal balance;

    public BankAccount() {
    }

    public BankAccount(Person owner) {
        this.owner = owner;
    }

    public Person getOwner() {
        return this.owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Bank getBank() {
        return this.bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String getDisplayValue() {
        return this.toString();
    }

    @Override
    public String toString() {
        return this.name != null ? this.name : this.number;
    }
}
