package be.superjoran.mint.wicket.upload;

import be.superjoran.common.VisibilityBehavior;
import be.superjoran.common.datatable.ColumnBuilderFactory;
import be.superjoran.common.datatable.DataTableBuilderFactory;
import be.superjoran.common.form.BaseForm;
import be.superjoran.common.link.LinkBuilderFactory;
import be.superjoran.common.model.LoadableListModel;
import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.searchresults.CsvFile;
import be.superjoran.mint.services.BankAccountService;
import be.superjoran.mint.services.CsvService;
import be.superjoran.mint.services.StatementService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.feedback.ExactLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;
import org.jetbrains.annotations.NotNull;

import javax.validation.ConstraintViolationException;
import java.util.List;

public class UploadCsvStep2Panel extends GenericPanel<List<CsvFile>> {
    private static final long serialVersionUID = -8480823644003013981L;

    @SpringBean
    private CsvService csvService;
    @SpringBean
    private StatementService statementService;

    private final IModel<Person> personIModel;
    private final IModel<List<BankAccount>> bankAccountListModel;

    public UploadCsvStep2Panel(String id, IModel<List<CsvFile>> model, IModel<Person> personIModel) {
        super(id, model);
        this.personIModel = personIModel;
        this.bankAccountListModel = new BankAccountCustomListModel(model);
    }

    @NotNull
    private static SerializableBiConsumer<AjaxRequestTarget, AjaxLink<Object>> saveStatementsAction() {
        return (ajaxRequestTarget, components) -> {
            UploadCsvStep2Panel parent = components.findParent(UploadCsvStep2Panel.class);
            try {
                parent.statementService.save(parent.csvService.uploadCSVFiles(parent.getModelObject(), parent.personIModel.getObject()));
                parent.setModelObject(null);
            } catch (ConstraintViolationException e) {
                e.getConstraintViolations().forEach(constraintViolation -> {
                    parent.error(e.getMessage());
                });
            } finally {
                ajaxRequestTarget.add(components.getPage());
            }
        };
    }

    private static class BankAccountCustomListModel extends LoadableListModel<BankAccount> {
        private static final long serialVersionUID = 4800483891654170501L;
        private final IModel<List<CsvFile>> csvFileListModel;
        @SpringBean
        private BankAccountService bankAccountService;

        private BankAccountCustomListModel(IModel<List<CsvFile>> csvFileListModel) {
            this.csvFileListModel = csvFileListModel;
        }

        @Override
        protected List<BankAccount> load() {
            List<BankAccount> bankAccountList = this.bankAccountService.findAll();
            bankAccountList.addAll(io.vavr.collection.List.ofAll(this.csvFileListModel.getObject())
                    .map(CsvFile::getBankAccount)
                    .distinctBy(BankAccount::getNumber)
                    .filter(bankAccount -> bankAccount.getUuid() == null)
                    .toJavaList());

            return bankAccountList;
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new VisibilityBehavior<>(c -> c.getDefaultModelObject() != null));

        BaseForm<List<CsvFile>> form = new BaseForm<>("form", this.getModel());
        form.add(new FencedFeedbackPanel("feedbackErrors", this, new ExactLevelFeedbackMessageFilter(FeedbackMessage.ERROR)));


        DataTableBuilderFactory.<CsvFile, String>simple()
                .addColumn(new LambdaColumn<>(new ResourceModel("file"), csvFile -> csvFile.getFileUrl().getName()))
                .addColumn(new LambdaColumn<>(new ResourceModel("bank.account"), csvFile -> csvFile.getBankAccount().getName()))
                .addColumn(ColumnBuilderFactory.custom(new ResourceModel("bank.account"), (id, model) -> new BankAccountDropdownPanel(id, model, this.personIModel, this.bankAccountListModel)))
                .attach(form, "datatable", this.getModel());

        this.add(form);

        LinkBuilderFactory.ajaxLink(saveStatementsAction())
                .usingDefaults()
                .attach(this, "save");

    }
}
