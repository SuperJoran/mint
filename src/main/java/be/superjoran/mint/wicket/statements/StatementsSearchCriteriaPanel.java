package be.superjoran.mint.wicket.statements;

import be.superjoran.common.form.BaseForm;
import be.superjoran.common.form.FormComponentBuilderFactory;
import be.superjoran.mint.domain.searchresults.StatementSearchCriteria;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;

public class StatementsSearchCriteriaPanel extends GenericPanel<StatementSearchCriteria> {

    public StatementsSearchCriteriaPanel(String id, IModel<StatementSearchCriteria> model) {
        super(id, model);

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
                        target.add(getComponent().findParent(StatementListPanel.class).getDataTable());
                    }
                }))
                .attach(form, "search", LambdaModel.of(this.getModel(), StatementSearchCriteria::getFuzzySearch, StatementSearchCriteria::setFuzzySearch));

        this.add(form);
    }
}
