package be.superjoran.mint.wicket.statements;

import be.superjoran.common.form.BaseForm;
import be.superjoran.common.form.FormComponentBuilderFactory;
import be.superjoran.common.link.LinkBuilderFactory;
import be.superjoran.mint.domain.Category;
import be.superjoran.mint.domain.searchresults.StatementSearchCriteria;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.ResourceModel;

import java.util.List;

public class StatementsSearchCriteriaPanel extends GenericPanel<StatementSearchCriteria> {

    private static final long serialVersionUID = -8660938297981923952L;
    private final IModel<List<Category>> categoryListModel;

    public StatementsSearchCriteriaPanel(String id, IModel<StatementSearchCriteria> model, IModel<List<Category>> categoryListModel) {
        super(id, model);
        this.categoryListModel = categoryListModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        BaseForm<StatementSearchCriteria> form = new BaseForm<>("form", this.getModel());

        FormComponentBuilderFactory.textField()
                .switchable(false)
                .noLabel()
                .configure(components -> components.add(new OnChangeAjaxBehavior() {
                    @Override
                    protected void onUpdate(AjaxRequestTarget target) {
                        target.add(this.getComponent().findParent(StatementListPanel.class).getDataTable());
                    }
                }))
                .attach(form, "search", LambdaModel.of(this.getModel(), StatementSearchCriteria::getFuzzySearch, StatementSearchCriteria::setFuzzySearch));

        LinkBuilderFactory.submitLink(((ajaxRequestTarget, components) -> {
            ajaxRequestTarget.add(components.findParent(StatementListPanel.class).getDataTable());
        }))
                .body(new ResourceModel("search"))
                .attach(form, "searchLink");

        FormComponentBuilderFactory.checkBox()
                .switchable(false)
                .body(new ResourceModel("filter.by.category"))
                .attach(form, "filterByCategory", LambdaModel.of(this.getModel(), StatementSearchCriteria::getFilterByCategory, StatementSearchCriteria::setFilterByCategory));

        FormComponentBuilderFactory.<Category>dropDown()
                .body(new ResourceModel("category"))
                .attach(form, "category", LambdaModel.of(this.getModel(), StatementSearchCriteria::getCategory, StatementSearchCriteria::setCategory), this.categoryListModel);


        this.add(form);
    }
}
