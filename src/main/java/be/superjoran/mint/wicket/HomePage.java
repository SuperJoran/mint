package be.superjoran.mint.wicket;

import be.superjoran.mint.domain.Person;
import be.superjoran.mint.wicket.bankaccounts.BankAccountListPanel;
import be.superjoran.mint.wicket.upload.CsvUploadPanel;
import org.apache.wicket.model.IModel;

public class HomePage extends BasePage<Person> {
    private static final long serialVersionUID = -649183163655825915L;

    public HomePage(IModel<Person> model) {
        super(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new BankAccountListPanel("bankAccounts", this.getModel()));

        this.add(new CsvUploadPanel("csvUploadPanel", this.getModel()));
    }
}
