package be.superjoran.mint.wicket.person;


import be.superjoran.common.form.BaseForm;
import be.superjoran.common.form.FormComponentBuilderFactory;
import be.superjoran.mint.domain.searchresults.PersonCandidate;
import be.superjoran.mint.services.PersonService;
import be.superjoran.mint.wicket.BasePage;
import org.apache.wicket.bean.validation.Property;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.feedback.ExactLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by Jorandeboever
 * Date: 01-Oct-16.
 */
public class RegisterPage extends BasePage<PersonCandidate> implements UnAuthorizedAllowed {

    @SpringBean
    private PersonService personService;

    public RegisterPage() {
        super(new Model<>(new PersonCandidate()));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new FencedFeedbackPanel("feedbackErrors", this, new ExactLevelFeedbackMessageFilter(FeedbackMessage.ERROR)));

        Form<PersonCandidate> form = new BaseForm<PersonCandidate>("form", this.getModel()) {
            @Override
            public void onSubmit() {
                super.onSubmit();
                RegisterPage.this.personService.create(RegisterPage.this.getModelObject());
                this.setResponsePage(new LoginPage());
            }
        };

        FormComponentBuilderFactory.textField()
                .usingDefaults()
                .required()
                .body(new ResourceModel("username"))
                .configure(c -> c.add(new PropertyValidator<>(new Property(PersonCandidate.class, "username"))))
                .attach(form, "username", LambdaModel.of(this.getModel(), PersonCandidate::getUsername, PersonCandidate::setUsername));

        FormComponentBuilderFactory.password()
                .usingDefaults()
                .required()
                .body(new ResourceModel("password"))
                .configure(c -> c.add(new PropertyValidator<>(new Property(PersonCandidate.class, "password"))))
                .attach(form, "password", LambdaModel.of(this.getModel(), PersonCandidate::getPassword, PersonCandidate::setPassword));

        form.add(new SubmitLink("save"));

        this.add(form);
    }
}
