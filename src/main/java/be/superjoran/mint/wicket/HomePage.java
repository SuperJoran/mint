package be.superjoran.mint.wicket;

import be.superjoran.common.datatable.DataTableBuilderFactory;
import be.superjoran.common.model.DomainObjectListModel;
import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.services.BankAccountService;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class HomePage extends WebPage {
    private static final long serialVersionUID = -649183163655825915L;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new BankAccountListPanel("bankAccounts", new Model<>(null)));
    }
}
