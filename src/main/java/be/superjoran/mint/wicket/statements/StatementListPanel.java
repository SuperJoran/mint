package be.superjoran.mint.wicket.statements;

import be.superjoran.common.datatable.ColumnBuilderFactory;
import be.superjoran.common.datatable.DataTableBuilderFactory;
import be.superjoran.common.form.BaseForm;
import be.superjoran.common.form.BaseForm.FormMode;
import be.superjoran.common.form.FormComponentBuilderFactory;
import be.superjoran.common.link.LinkBuilderFactory;
import be.superjoran.common.model.DomainObjectListModel;
import be.superjoran.mint.domain.Category;
import be.superjoran.mint.domain.Statement;
import be.superjoran.mint.services.CategoryService;
import be.superjoran.mint.services.StatementService;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StatementListPanel extends GenericPanel<List<Statement>> {
    private static final long serialVersionUID = -2535058452494166227L;
    private static final String FORM_ID = "form";
    private static final String DATATABLE_ID = "datatable";
    private static final String CHECK_GROUP_ID = "checkGroup";

    @SpringBean
    private StatementService statementService;
    @SpringBean
    private CategoryService categoryService;

    private final IModel<List<Statement>> selectedStatementsModel;
    private final IModel<Category> chosenCategoryModel;
    private final IModel<List<Statement>> filteredStatementsModel;
    private final IModel<String> searchModel;

    public StatementListPanel(String id, IModel<List<Statement>> model) {
        super(id, model);
        this.selectedStatementsModel = new ListModel<>(new ArrayList<>());
        this.chosenCategoryModel = new Model<>(null);
        this.searchModel = new Model<>("");
        this.filteredStatementsModel = new FilteredStatementsModel(model, this.searchModel);

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.setOutputMarkupId(true);

        this.add(new StatementsSearchCriteriaPanel("search", this.searchModel));

        BaseForm<List<Statement>> dataTableForm = new BaseForm<>(FORM_ID, this.getModel());

        CheckGroup<Statement> checkGroup = new CheckGroup<Statement>(CHECK_GROUP_ID, this.selectedStatementsModel);

        DataTableBuilderFactory.<Statement, String>simple()
                .addColumn(ColumnBuilderFactory.<Statement, String>check().build(new ResourceModel("category")))
                .addColumn(new LambdaColumn<>(new ResourceModel("statement"), Statement::getOriginatingAccount))
                .addColumn(new LambdaColumn<>(new ResourceModel("to"), Statement::getDestinationAccountNumber))
                .addColumn(new LambdaColumn<>(new ResourceModel("description"), Statement::getDescription))
                .addColumn(new LambdaColumn<>(new ResourceModel("category"), Statement::getCategory))
                .attach(checkGroup, DATATABLE_ID, this.filteredStatementsModel);
        dataTableForm.add(checkGroup);

        this.stickyFooter(dataTableForm);
        this.add(dataTableForm);
    }

    private void stickyFooter(MarkupContainer parent) {
        WebMarkupContainer stickyFooter = new WebMarkupContainer("sticky");
        LinkBuilderFactory.submitLink(save())
                .attach(stickyFooter, "save");

        FormComponentBuilderFactory.<Category>dropDown()
                .body(new ResourceModel("category"))
                .attach(stickyFooter, "category", this.chosenCategoryModel, new DomainObjectListModel<>(this.categoryService));

        parent.add(stickyFooter);
    }

    @SuppressWarnings("unchecked")
    private BaseForm<List<Statement>> getForm() {
        return (BaseForm<List<Statement>>) this.get(FORM_ID);
    }

    @SuppressWarnings({"unchecked"})
    DataTable<Statement, String> getDataTable() {
        return (DataTable<Statement, String>) this.getForm().get(CHECK_GROUP_ID).get(DATATABLE_ID);
    }

    @NotNull
    private static SerializableBiConsumer<AjaxRequestTarget, AjaxSubmitLink> save() {
        return (ajaxRequestTarget, components) -> {
            StatementListPanel parent = components.findParent(StatementListPanel.class);

            parent.statementService.assignCategories(parent.selectedStatementsModel.getObject(), parent.chosenCategoryModel.getObject());
            parent.getModel().setObject(null);
            parent.getForm().getFormModeModel().setObject(FormMode.EDIT);
            ajaxRequestTarget.add(parent);
        };
    }
}
