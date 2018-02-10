package be.superjoran.mint.wicket;

import be.superjoran.common.datatable.DataTableBuilderFactory;
import be.superjoran.common.model.DomainObjectListModel;
import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.services.BankAccountService;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class BankAccountListPanel extends GenericPanel<Person> {
    private static final long serialVersionUID = -3753470807322711474L;

    @SpringBean
    private BankAccountService bankAccountService;

    public BankAccountListPanel(String id, IModel<Person> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        DataTableBuilderFactory.<BankAccount, String>simple()
                .addColumn(new LambdaColumn<>(new ResourceModel("account"), BankAccount::getBalance))
                .attach(this,"dataTable", new DomainObjectListModel<>(this.bankAccountService));
    }
}
