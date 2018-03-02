package be.superjoran.mint.wicket;

import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.searchresults.CsvFile;
import be.superjoran.mint.wicket.bankaccounts.BankAccountListPanel;
import be.superjoran.mint.wicket.upload.UploadCsvStep1Panel;
import be.superjoran.mint.wicket.upload.UploadCsvStep2Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

import java.util.List;

public class HomePage extends BasePage<Person> {
    private static final long serialVersionUID = -649183163655825915L;

    public HomePage(IModel<Person> model) {
        super(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new BankAccountListPanel("bankAccounts", this.getModel()));

        IModel<List<CsvFile>> listIModel = new ListModel<>(null);
        this.add(new UploadCsvStep1Panel("fileUpload", listIModel, this.getModel()));

        this.add(new UploadCsvStep2Panel("fileUploads", listIModel, this.getModel()));
    }
}
