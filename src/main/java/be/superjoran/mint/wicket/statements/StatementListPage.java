package be.superjoran.mint.wicket.statements;

import be.superjoran.mint.domain.Person;
import be.superjoran.mint.wicket.BasePage;
import org.apache.wicket.model.IModel;

public class StatementListPage extends BasePage<Person> {
    private static final long serialVersionUID = -6066087619138624356L;

    public StatementListPage(IModel<Person> model) {
        super(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new StatementListPanel("statements", new StatementListModel(this.getModel())));
    }
}
