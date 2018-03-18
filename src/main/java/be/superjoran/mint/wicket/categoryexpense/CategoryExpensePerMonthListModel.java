package be.superjoran.mint.wicket.categoryexpense;

import be.superjoran.common.model.LoadableListModel;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.searchresults.CategoryExpense;
import be.superjoran.mint.services.CategoryExpenseService;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class CategoryExpensePerMonthListModel extends LoadableListModel<CategoryExpense> {

    private final IModel<Person> personIModel;
    @SpringBean
    private CategoryExpenseService categoryExpenseService;

    public CategoryExpensePerMonthListModel(IModel<Person> personIModel) {
        this.personIModel = personIModel;
    }

    @Override
    protected List<CategoryExpense> load() {
        return this.categoryExpenseService.findCategoryExpensesPerMonthByOwner(this.personIModel.getObject());
    }
}
