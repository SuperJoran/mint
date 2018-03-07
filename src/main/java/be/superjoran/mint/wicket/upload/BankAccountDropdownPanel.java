package be.superjoran.mint.wicket.upload;

import be.superjoran.common.form.FormComponentBuilderFactory;
import be.superjoran.common.link.LinkBuilderFactory;
import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.searchresults.BankAccountCandidate;
import be.superjoran.mint.domain.searchresults.CsvFile;
import be.superjoran.mint.wicket.bankaccounts.BankAccountDetailPanel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BankAccountDropdownPanel extends GenericPanel<CsvFile> {
    private static final long serialVersionUID = -1498274906340537352L;

    private static final String MODAL_WINDOW_ID = "modal";
    private static final String BANKACCOUNT_DROPDOWN_ID = "bankaccount";
    private final IModel<Person> personIModel;
    private final IModel<List<BankAccount>> bankAccountListModel;

    BankAccountDropdownPanel(String id, IModel<CsvFile> model, IModel<Person> personIModel, IModel<List<BankAccount>> bankAccountListModel) {
        super(id, model);
        this.personIModel = personIModel;

        this.bankAccountListModel = bankAccountListModel;
    }

    @NotNull
    private static SerializableBiConsumer<AjaxRequestTarget, AjaxLink<Object>> openModalWindow() {
        return (ajaxRequestTarget, components) -> components.findParent(BankAccountDropdownPanel.class).getModalWindow().show(ajaxRequestTarget);
    }

    @NotNull
    private static SerializableBiConsumer<AjaxRequestTarget, Component> afterSave() {
        return (ajaxRequestTarget, component) -> {
            BankAccountDropdownPanel parent = component.findParent(BankAccountDropdownPanel.class);
            parent.bankAccountListModel.setObject(null);
            ajaxRequestTarget.add(parent.getDropdownContainer());

            component.findParent(ModalWindow.class).close(ajaxRequestTarget);
        };
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        FormComponentBuilderFactory.<BankAccount>dropDown()
                .container()
                .attach(this,
                        BANKACCOUNT_DROPDOWN_ID,
                        LambdaModel.of(this.getModel(), CsvFile::getBankAccount, CsvFile::setBankAccount),
                        this.bankAccountListModel
                );

        this.modalWindow();

        LinkBuilderFactory.ajaxLink(openModalWindow())
                .usingDefaults()
                .body(new ResourceModel("new"))
                .attach(this, "new");

    }

    private Component getDropdownContainer() {
        return this.get(BANKACCOUNT_DROPDOWN_ID + "-container");
    }

    private ModalWindow getModalWindow() {
        return (ModalWindow) this.get(MODAL_WINDOW_ID);
    }

    private void modalWindow() {
        Model<BankAccountCandidate> model = Model.of(new BankAccountCandidate(this.personIModel.getObject()));
        ModalWindow modalWindow = new ModalWindow(MODAL_WINDOW_ID, model);
        modalWindow.setContent(new BankAccountDetailPanel(modalWindow.getContentId(), model, afterSave()));
        this.add(modalWindow);
    }
}
