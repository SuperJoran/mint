package be.superjoran.mint.wicket.DestinationAccount;

import be.superjoran.common.datatable.DataTableBuilderFactory;
import be.superjoran.mint.domain.searchresults.DestinationCategory;
import be.superjoran.mint.wicket.bankaccounts.DestinationCategoryListModel;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.ResourceModel;

import java.util.List;

public class DestinationAccountListpanel extends GenericPanel<List<DestinationCategory>> {
    public DestinationAccountListpanel(String id) {
        super(id, new DestinationCategoryListModel());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        DataTableBuilderFactory.<DestinationCategory, String>simple()
                .addColumn(new LambdaColumn<>(new ResourceModel("name"), DestinationCategory::getDestinationAccountNumber))
                .attach(this, "destinationAccounts", new DestinationCategoryListModel());
    }
}
