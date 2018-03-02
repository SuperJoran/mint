package be.superjoran.mint.wicket.upload;

import be.superjoran.common.form.FormComponentBuilderFactory;
import be.superjoran.common.model.DomainObjectListModel;
import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.searchresults.CsvFile;
import be.superjoran.mint.services.BankAccountService;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class BankAccountDropdownPanel extends GenericPanel<CsvFile> {
    private static final long serialVersionUID = -1498274906340537352L;
    private final IModel<Person> personIModel;
    @SpringBean
    private BankAccountService bankAccountService;

    BankAccountDropdownPanel(String id, IModel<CsvFile> model, IModel<Person> personIModel) {
        super(id, model);
        this.personIModel = personIModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        FormComponentBuilderFactory.<BankAccount>dropDown()
                .attach(this,
                        "bankaccount",
                        LambdaModel.of(this.getModel(), CsvFile::getBankAccount, CsvFile::setBankAccount),
                        new DomainObjectListModel<>(this.bankAccountService, s -> s.findAllByOwner(this.personIModel.getObject()))
                );
    }
}
