package be.superjoran.mint.domain;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

/**
 * Created by Jorandeboever
 * Date: 01-Oct-16.
 */
@Entity
@Table(name = "T_BANKACCOUNT", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"NUMBER", "ADMINISTRATOR_UUID"}
        )
})
public class BankAccount extends DomainObject {
    private static final long serialVersionUID = -4411985824034001947L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ADMINISTRATOR_UUID")
    private Person administrator;

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

    @ManyToOne
    @JoinColumnOrFormula(formula = @JoinFormula(value = "(SELECT MAX(statement.CATEGORY_UUID) FROM T_STATEMENT statement WHERE statement.DESTINATIONACCOUNT_UUID = UUID)", referencedColumnName = "UUID"))
    @Access(AccessType.FIELD)
    private Category category;

    public Person getOwner() {
        return this.owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Person getAdministrator() {
        return this.administrator;
    }

    public void setAdministrator(Person administrator) {
        this.administrator = administrator;
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

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
