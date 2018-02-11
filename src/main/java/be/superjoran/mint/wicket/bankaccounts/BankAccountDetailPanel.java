package be.superjoran.mint.wicket.bankaccounts;

import be.superjoran.common.form.BaseForm;
import be.superjoran.common.form.FormComponentBuilderFactory;
import be.superjoran.common.link.LinkBuilderFactory;
import be.superjoran.mint.domain.BankAccount;
import be.superjoran.mint.services.BankAccountService;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.ResourceModel;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;
import org.jetbrains.annotations.NotNull;

public class BankAccountDetailPanel extends GenericPanel<BankAccount> {
    private BankAccountService bankAccountService;

    public BankAccountDetailPanel(String id, IModel<BankAccount> model) {
        super(id, model);
    }


    @Override
    protected void onInitialize() {
        super.onInitialize();

        BaseForm<BankAccount> form = new BaseForm<>("form", this.getModel());

        FormComponentBuilderFactory.textField()
                .body(new ResourceModel("number"))
                .attach(form, "number", LambdaModel.of(form.getModel(), BankAccount::getNumber, BankAccount::setNumber));

        FormComponentBuilderFactory.textField()
                .body(new ResourceModel("name"))
                .attach(form, "name", LambdaModel.of(form.getModel(), BankAccount::getName, BankAccount::setName));

        LinkBuilderFactory.submitLink(saveAction())
                .attach(form, "save");

        this.add(form);
    }

    @NotNull
    private static SerializableBiConsumer<AjaxRequestTarget, AjaxSubmitLink> saveAction() {
        return (ajaxRequestTarget, components) -> {
            BankAccountDetailPanel parent = components.findParent(BankAccountDetailPanel.class);
            parent.bankAccountService.save(parent.getModelObject());
        };
    }
}
