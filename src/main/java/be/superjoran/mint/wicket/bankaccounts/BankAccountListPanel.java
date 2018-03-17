package be.superjoran.mint.wicket.bankaccounts;

import be.superjoran.common.datatable.DataTableBuilderFactory;
import be.superjoran.common.link.LinkBuilderFactory;
import be.superjoran.common.model.DomainObjectListModel;
import be.superjoran.mint.domain.Bank;
import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.searchresults.BankAccountCandidate;
import be.superjoran.mint.services.BankAccountService;
import de.agilecoders.wicket.core.markup.html.bootstrap.list.BootstrapListView;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.stream.Collectors;

public class BankAccountListPanel extends GenericPanel<Person> {
    private static final long serialVersionUID = -3753470807322711474L;

    @SpringBean
    private BankAccountService bankAccountService;
    private final DomainObjectListModel<BankAccount, BankAccountService> bankAccountListModel;

    public BankAccountListPanel(String id, IModel<Person> model) {
        super(id, model);
        this.bankAccountListModel = new DomainObjectListModel<>(this.bankAccountService, s -> s.findAllByOwner(this.getModelObject()));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        LinkBuilderFactory.pageLink(() -> new BankAccountDetailPage(Model.of(new BankAccountCandidate(this.getModelObject()))))
                .attach(this, "new");

        this.add(new CustomListView("banks"));

    }

    private class CustomListView extends BootstrapListView<Bank> {
        private static final long serialVersionUID = 4367157751933026122L;

        public CustomListView(String id) {
            super(id, Bank.Companion.getBanks());
        }

        @Override
        protected void populateItem(ListItem<Bank> item) {
            item.add(new Label("name", item.getModel()));

            DataTableBuilderFactory.<BankAccount, String>simple()
                    .addColumn(new LambdaColumn<>(new ResourceModel("name"), BankAccount::getName))
                    .addColumn(new LambdaColumn<>(new ResourceModel("number"), BankAccount::getNumber))
                    .addColumn(new LambdaColumn<>(new ResourceModel("balance"), BankAccount::getBalance))
                    .attach(item, "dataTable", LambdaModel.of(BankAccountListPanel.this.bankAccountListModel, list -> list.stream()
                            .filter(ba -> ba.getBank() == item.getModelObject())
                            .collect(Collectors.toList()))
                    );
        }
    }
}
