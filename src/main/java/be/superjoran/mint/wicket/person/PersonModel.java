package be.superjoran.mint.wicket.person;

import be.superjoran.common.model.LoadableModel;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.services.PersonService;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by Jorandeboever
 * Date: 01-Oct-16.
 */
public class PersonModel extends LoadableModel<Person> {
    private static final long serialVersionUID = 3249483672329601983L;

    @SpringBean
    private PersonService personService;

    private final IModel<String> idModel;

    public PersonModel(IModel<String> idModel) {
        super();
        this.idModel = idModel;
    }

    @Override
    protected Person load() {
        return this.personService.findOne(this.idModel.getObject()).orElse(null);
    }
}
