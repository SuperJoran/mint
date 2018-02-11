package be.superjoran.mint.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Jorandeboever
 * Date: 01-Oct-16.
 */
@Entity
@Table(name = "T_STATEMENT")
public class Statement extends DomainObject {
    private static final long serialVersionUID = -5710605280145439611L;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "ORIGINATINGACCOUNT_UUID")
    private BankAccount originatingAccount;

    @Column(name = "DESTINATIONACCOUNT_NUMBER")
    private String destinationAccountNumber;

    private LocalDate date;
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_UUID")
    private Category category;

    private String description;

    @Column(name = "CSV_LINE")
    private String csvLine;

    public Statement() {
    }

    public Statement(String description) {
        this.description = description;
    }

    public BankAccount getOriginatingAccount() {
        return this.originatingAccount;
    }

    public void setOriginatingAccount(BankAccount originatingAccount) {
        this.originatingAccount = originatingAccount;
    }

    public String getDestinationAccountNumber() {
        return this.destinationAccountNumber;
    }

    public void setDestinationAccountNumber(String destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        if (this.amount == null) {
            this.amount = BigDecimal.ZERO;
        }
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCsvLine() {
        return this.csvLine;
    }

    public void setCsvLine(String csvLine) {
        this.csvLine = csvLine;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
