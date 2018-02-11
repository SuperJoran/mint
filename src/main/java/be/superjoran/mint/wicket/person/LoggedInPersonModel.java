package be.superjoran.mint.wicket.person;


import be.superjoran.common.model.LoadableModel;
import be.superjoran.mint.domain.Person;

/**
 * Created by Jorandeboever
 * Date: 14-Jan-17.
 */
public class LoggedInPersonModel extends LoadableModel<Person> {

    @Override
    protected Person load() {
        return CustomSession.get().getLoggedInPerson();
    }
}
