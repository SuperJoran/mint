package be.superjoran.mint.wicket.bankaccounts;

import be.superjoran.common.form.BaseForm;
import be.superjoran.common.form.FormComponentBuilderFactory;
import be.superjoran.common.link.LinkBuilderFactory;
import be.superjoran.mint.domain.Bank;
import be.superjoran.mint.domain.searchresults.BankAccountCandidate;
import be.superjoran.mint.services.BankAccountService;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.bean.validation.Property;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.feedback.ExactLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.danekja.java.util.function.serializable.SerializableBiConsumer;
import org.jetbrains.annotations.NotNull;

public class BankAccountDetailPanel extends GenericPanel<BankAccountCandidate> {
    @SpringBean
    private BankAccountService bankAccountService;

    private final SerializableBiConsumer<AjaxRequestTarget, Component> onSaveConsumer;

    public BankAccountDetailPanel(String id, IModel<BankAccountCandidate> model) {
        super(id, model);
        this.onSaveConsumer = ((ajaxRequestTarget, component) -> {
        });
    }

    public BankAccountDetailPanel(String id, IModel<BankAccountCandidate> model, SerializableBiConsumer<AjaxRequestTarget, Component> onSaveConsumer) {
        super(id, model);
        this.onSaveConsumer = onSaveConsumer;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();


        BaseForm<BankAccountCandidate> form = new BaseForm<>("form", this.getModel());
        form.add(new FencedFeedbackPanel("feedbackErrors", this, new ExactLevelFeedbackMessageFilter(FeedbackMessage.ERROR)));

        FormComponentBuilderFactory.textField()
                .body(new ResourceModel("number"))
                .configure(c -> c.add(new PropertyValidator<>(new Property(BankAccountCandidate.class, "number"))))
                .attach(form, "number", LambdaModel.of(form.getModel(), BankAccountCandidate::getNumber, BankAccountCandidate::setNumber));

        FormComponentBuilderFactory.textField()
                .body(new ResourceModel("name"))
                .configure(c -> c.add(new PropertyValidator<>(new Property(BankAccountCandidate.class, "name"))))
                .attach(form, "name", LambdaModel.of(form.getModel(), BankAccountCandidate::getName, BankAccountCandidate::setName));

        FormComponentBuilderFactory.<Bank>dropDown()
                .usingDefaults()
                .configure(c -> c.add(new PropertyValidator<>(new Property(BankAccountCandidate.class, "bank"))))
                .attach(form, "bank", LambdaModel.of(form.getModel(), BankAccountCandidate::getBank, BankAccountCandidate::setBank), new ListModel<>(Bank.Companion.getBanks()));

        LinkBuilderFactory.submitLink(saveAction().andThen(this.onSaveConsumer))
                .attach(form, "save");

        this.add(form);
    }

    @NotNull
    private static SerializableBiConsumer<AjaxRequestTarget, AjaxSubmitLink> saveAction() {
        return (ajaxRequestTarget, components) -> {
            BankAccountDetailPanel parent = components.findParent(BankAccountDetailPanel.class);
            parent.bankAccountService.createOrUpdate(parent.getModelObject());
        };
    }
}
