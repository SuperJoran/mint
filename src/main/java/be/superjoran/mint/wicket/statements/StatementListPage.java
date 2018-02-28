package be.superjoran.mint.wicket.statements;

import be.superjoran.common.link.LinkBuilderFactory;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.services.AssignCategoriesService;
import be.superjoran.mint.wicket.BasePage;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;
import org.jetbrains.annotations.NotNull;

public class StatementListPage extends BasePage<Person> {
    private static final long serialVersionUID = -6066087619138624356L;
    private static final String STATEMENTS_PANEL_ID = "statements";

    @SpringBean
    private AssignCategoriesService assignCategoriesService;

    public StatementListPage(IModel<Person> model) {
        super(model);
    }

    @NotNull
    private static SerializableBiConsumer<AjaxRequestTarget, AjaxLink<Object>> assignCategories() {
        return (ajaxRequestTarget, components) -> {
            StatementListPage parent = components.findParent(StatementListPage.class);
            parent.assignCategoriesService.assignCategoriesForPerson(parent.getModelObject());
            ajaxRequestTarget.add(parent.getStatementListPanel());
        };
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        LinkBuilderFactory.ajaxLink(assignCategories())
                .usingDefaults()
                .attach(this, "assignCategories");

        this.add(new StatementListPanel(STATEMENTS_PANEL_ID, new StatementListModel(this.getModel())));
    }

    public Component getStatementListPanel() {
        return this.get(STATEMENTS_PANEL_ID);
    }
}
