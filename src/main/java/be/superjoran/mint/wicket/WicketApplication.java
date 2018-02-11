package be.superjoran.mint.wicket;

import be.superjoran.mint.wicket.person.AuthorizationRequired;
import be.superjoran.mint.wicket.person.CustomSession;
import be.superjoran.mint.wicket.person.LoginPage;
import be.superjoran.mint.wicket.person.PersonListPage;
import be.superjoran.mint.wicket.person.UnAuthorizedAllowed;
import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.IAuthorizationStrategy.AllowAllAuthorizationStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class WicketApplication extends WebApplication{
    @Override
    public Class<? extends Page> getHomePage() {
        return PersonListPage.class;
    }

    @Override
    protected void init() {
        super.init();

        this.getSecuritySettings().setAuthorizationStrategy(new AllowAllAuthorizationStrategy() {
            @Override
            public <T extends IRequestableComponent> boolean isInstantiationAuthorized(
                    Class<T> componentClass) {
                if (AuthorizationRequired.class.isAssignableFrom(componentClass) && !UnAuthorizedAllowed.class.isAssignableFrom(componentClass)) {
                    if (CustomSession.get().isSignedIn()) {
                        return true;
                    }
                    throw new RestartResponseAtInterceptPageException(LoginPage.class);
                }

                return true;
            }
        });


        super.getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        Bootstrap.install(this, new BootstrapSettings());

    }

    @Override
    public Session newSession(Request request, Response response) {
        return new CustomSession(request);
    }
}
