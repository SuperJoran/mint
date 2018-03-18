package be.superjoran.mint.wicket.categoryexpense;

import be.superjoran.common.datatable.DataTableBuilderFactory;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.searchresults.CategoryExpense;
import de.agilecoders.wicket.core.markup.html.bootstrap.tabs.BootstrapTabbedPanel;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CategoryExpensePanel extends GenericPanel<Person> {

    private final IModel<List<CategoryExpense>> categoryExpenseListModel;

    public CategoryExpensePanel(String id, IModel<Person> model) {
        super(id, model);
        this.categoryExpenseListModel = new CategoryExpensePerYearListModel(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new BootstrapTabbedPanel<AbstractTab>("tabbedPanel", Arrays.asList(
                new AbstractTab(new ResourceModel("by.year")) {
                    private static final long serialVersionUID = 2948429044249274343L;

                    @Override
                    public WebMarkupContainer getPanel(String panelId) {
                        return new CustomTabbedPanel(panelId, new CategoryExpensePerYearListModel(CategoryExpensePanel.this.getModel()));
                    }
                },
                new AbstractTab(new ResourceModel("by.month")) {
                    private static final long serialVersionUID = 4191178016644322771L;

                    @Override
                    public WebMarkupContainer getPanel(String panelId) {
                        return new CustomTabbedPanel(panelId, new CategoryExpensePerMonthListModel(CategoryExpensePanel.this.getModel()));
                    }
                }
        )));


    }

    private class CustomTabbedPanel extends GenericPanel<List<CategoryExpense>> {
        private static final long serialVersionUID = 3751435918943357837L;

        public CustomTabbedPanel(String id, IModel<List<CategoryExpense>> model) {
            super(id, model);
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();

            DataTableBuilderFactory.<CategoryExpense, String>simple()
                    .addColumn(new LambdaColumn<>(new ResourceModel("category"), CategoryExpense::getCategory))
                    .addColumn(new LambdaColumn<>(new ResourceModel("year"), categoryExpense -> categoryExpense.getSimpleDate().format(DateTimeFormatter.ofPattern("yyyy"))))
                    .addColumn(new LambdaColumn<>(new ResourceModel("sum"), CategoryExpense::getSum))
                    .attach(this, "datatable", this.getModel());
        }
    }
}
