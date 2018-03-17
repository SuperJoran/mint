package be.superjoran.mint.wicket.categoryexpense;

import be.superjoran.mint.domain.Person;
import be.superjoran.mint.wicket.BasePage;
import org.apache.wicket.model.IModel;

public class CategoryExpensePage extends BasePage<Person> {

    public CategoryExpensePage(IModel<Person> model) {
        super(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.add(new CategoryExpensePanel("categoryExpensesPanel", this.getModel()));
    }
}
