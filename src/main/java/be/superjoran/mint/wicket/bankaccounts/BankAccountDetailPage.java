package be.superjoran.mint.wicket.bankaccounts;

import be.superjoran.mint.domain.searchresults.BankAccountCandidate;
import be.superjoran.mint.wicket.BasePage;
import org.apache.wicket.model.IModel;

public class BankAccountDetailPage extends BasePage<BankAccountCandidate> {

    public BankAccountDetailPage(IModel<BankAccountCandidate> model) {
        super(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new BankAccountDetailPanel("detailPanel", this.getModel()));
    }
}
