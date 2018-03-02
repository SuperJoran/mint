package be.superjoran.mint.wicket.upload;

import be.superjoran.common.VisibilityBehavior;
import be.superjoran.common.datatable.ColumnBuilderFactory;
import be.superjoran.common.datatable.DataTableBuilderFactory;
import be.superjoran.common.form.BaseForm;
import be.superjoran.common.link.LinkBuilderFactory;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.searchresults.CsvFile;
import be.superjoran.mint.services.CsvService;
import be.superjoran.mint.services.StatementService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UploadCsvStep2Panel extends GenericPanel<List<CsvFile>> {
    private static final long serialVersionUID = -8480823644003013981L;

    @SpringBean
    private CsvService csvService;
    @SpringBean
    private StatementService statementService;

    private final IModel<Person> personIModel;

    public UploadCsvStep2Panel(String id, IModel<List<CsvFile>> model, IModel<Person> personIModel) {
        super(id, model);
        this.personIModel = personIModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new VisibilityBehavior<>(c -> c.getDefaultModelObject() != null));

        BaseForm<List<CsvFile>> form = new BaseForm<>("form", this.getModel());

        DataTableBuilderFactory.<CsvFile, String>simple()
                .addColumn(new LambdaColumn<>(new ResourceModel("file"), csvFile -> csvFile.getFileUrl().getName()))
                .addColumn(new LambdaColumn<>(new ResourceModel("bank.account"), csvFile -> csvFile.getBankAccount().getName()))
                .addColumn(ColumnBuilderFactory.custom(new ResourceModel("bank.account"), (id, model) -> new BankAccountDropdownPanel(id, model, this.personIModel)))
                .attach(form, "datatable", this.getModel());

        this.add(form);

        LinkBuilderFactory.ajaxLink(saveStatementsAction())
                .usingDefaults()
                .attach(this, "save");

    }

    @NotNull
    private static SerializableBiConsumer<AjaxRequestTarget, AjaxLink<Object>> saveStatementsAction() {
        return (ajaxRequestTarget, components) -> {
            UploadCsvStep2Panel parent = components.findParent(UploadCsvStep2Panel.class);
            parent.statementService.save(parent.csvService.uploadCSVFiles(parent.getModelObject()));
            parent.setModelObject(null);
            ajaxRequestTarget.add(components.getPage());
        };
    }
}
