package be.superjoran.mint.wicket;


import be.superjoran.common.VisibilityBehavior;
import be.superjoran.common.link.LinkBuilderFactory;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.wicket.person.AuthorizationRequired;
import be.superjoran.mint.wicket.person.CustomSession;
import be.superjoran.mint.wicket.person.LoggedInPersonModel;
import be.superjoran.mint.wicket.person.LoginPage;
import be.superjoran.mint.wicket.person.LogoutPage;
import be.superjoran.mint.wicket.person.RegisterPage;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeCssReference;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;

/**
 * Created by Ghostwritertje
 * Date: 30-Sep-16.
 */
public abstract class BasePage<T> extends GenericWebPage<T> implements AuthorizationRequired {
    private static final long serialVersionUID = 8757457619853670497L;
    private final LoggedInPersonModel loggedInPersonModel = new LoggedInPersonModel();

    protected BasePage() {
        super();
    }

    protected BasePage(IModel<T> model) {
        super(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new BookmarkablePageLink("registerLink", RegisterPage.class)
                .add(new VisibilityBehavior<>(component -> CustomSession.get().getLoggedInPerson() == null)));
        this.add(new BookmarkablePageLink("loginLink", LoginPage.class)
                .add(new VisibilityBehavior<>(component -> CustomSession.get().getLoggedInPerson() == null)));

        LinkBuilderFactory.pageLink(LogoutPage::new)
                .attach(this, "logoutLink");

        LinkBuilderFactory.pageLink(() -> new HomePage(this.loggedInPersonModel))
                .attach(this, "home");

        this.add(new Label("username", LambdaModel.of(this.loggedInPersonModel, Person::getUsername))
                .add(new VisibilityBehavior<>(c -> c.getDefaultModelObject() != null))
        );
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(new CssReferenceHeaderItem(FontAwesomeCssReference.instance(), null, null, null));
    }
}
