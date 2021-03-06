package be.superjoran.mint.wicket.statements;

import be.superjoran.common.link.LinkBuilderFactory;
import be.superjoran.common.model.LoadableModel;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.Statement;
import be.superjoran.mint.services.AssignCategoriesService;
import be.superjoran.mint.services.DestinationCategoryService;
import be.superjoran.mint.wicket.BasePage;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StatementListPage extends BasePage<Person> {
    private static final long serialVersionUID = -6066087619138624356L;

    private static final String STATEMENTS_PANEL_ID = "statements";

    @SpringBean
    private AssignCategoriesService assignCategoriesService;
    @SpringBean
    private DestinationCategoryService destinationCategoryService;

    private final StatementListModel statementListModel;
    private final IModel<String> numberOfStatementsThatCanBeAssignedModel;

    public StatementListPage(IModel<Person> model) {
        super(model);
        this.statementListModel = new StatementListModel(model);
        this.numberOfStatementsThatCanBeAssignedModel = new NumberOfStatementsThatCanBeAssignedModel(model, this.statementListModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new AutoAssignOptionPanel("autoAssign", this.getModel()));

        LinkBuilderFactory.ajaxLink(assignCategories().andThen(reloadPage()))
                .usingDefaults()
                .body(new StringResourceModel("assign.categories", this.numberOfStatementsThatCanBeAssignedModel))
                .attach(this, "assignCategories");

        LinkBuilderFactory.ajaxLink(assignInternalCategories().andThen(reloadPage()))
                .usingDefaults()
                .body(new ResourceModel("assign.internal.categories"))
                .attach(this, "assignInternalCategories");

        this.add(new StatementListPanel(STATEMENTS_PANEL_ID, this.statementListModel, reloadPage()));
    }

    @NotNull
    private static SerializableBiConsumer<AjaxRequestTarget, AjaxLink<Object>> assignInternalCategories() {
        return (ajaxRequestTarget, components) -> {
            StatementListPage parent = components.findParent(StatementListPage.class);
            parent.destinationCategoryService.assignInternalCategory(parent.getModelObject());
        };
    }

    @NotNull
    private static SerializableBiConsumer<AjaxRequestTarget, AjaxLink<Object>> assignCategories() {
        return (ajaxRequestTarget, components) -> {
            StatementListPage parent = components.findParent(StatementListPage.class);
            parent.assignCategoriesService.assignCategoriesForPerson(parent.getModelObject());
        };
    }

    @NotNull
    private static <X extends Component> SerializableBiConsumer<AjaxRequestTarget, X> reloadPage() {
        return (ajaxRequestTarget, components) -> {
            StatementListPage parent = components.findParent(StatementListPage.class);
            parent.statementListModel.setObject(null);
            parent.numberOfStatementsThatCanBeAssignedModel.setObject(null);
            ajaxRequestTarget.add(parent);
        };
    }

    private Component getStatementListPanel() {
        return this.get(STATEMENTS_PANEL_ID);
    }

    private static class NumberOfStatementsThatCanBeAssignedModel extends LoadableModel<String> {
        private static final long serialVersionUID = 3196793775550265991L;
        private final IModel<Person> personModel;
        private final IModel<List<Statement>> statementListModel;
        @SpringBean
        private DestinationCategoryService destinationCategoryService;

        private NumberOfStatementsThatCanBeAssignedModel(IModel<Person> personModel, IModel<List<Statement>> statementListModel) {
            this.personModel = personModel;
            this.statementListModel = statementListModel;
        }

        @Override
        protected String load() {
            return String.format(
                    "(%s/%s)",
                    this.destinationCategoryService.findNumberOfStatementsThatCanBeAssigned(this.personModel.getObject()),
                    this.statementListModel.getObject().stream().filter(s -> s.getCategory() == null).count()
            );
        }

    }
}
