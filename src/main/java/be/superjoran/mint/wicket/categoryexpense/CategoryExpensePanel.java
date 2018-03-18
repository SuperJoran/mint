package be.superjoran.mint.wicket.categoryexpense;

import be.superjoran.common.datatable.DataTableBuilderFactory;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.searchresults.CategoryExpense;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CategoryExpensePanel extends GenericPanel<Person> {

    private final IModel<List<CategoryExpense>> categoryExpenseListModel;

    public CategoryExpensePanel(String id, IModel<Person> model) {
        super(id, model);
        this.categoryExpenseListModel = new CategoryExpenseListModel(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        DataTableBuilderFactory.<CategoryExpense, String>simple()
                .addColumn(new LambdaColumn<>(new ResourceModel("category"), CategoryExpense::getCategory))
                .addColumn(new LambdaColumn<>(new ResourceModel("year"), categoryExpense -> categoryExpense.getSimpleDate().format(DateTimeFormatter.ofPattern("yyyy"))))
                .addColumn(new LambdaColumn<>(new ResourceModel("sum"), CategoryExpense::getSum))
                .attach(this, "datatable", this.categoryExpenseListModel);
    }
}
