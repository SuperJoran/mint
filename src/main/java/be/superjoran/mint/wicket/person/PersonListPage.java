package be.superjoran.mint.wicket.person;


import be.superjoran.common.datatable.DataTableBuilderFactory;
import be.superjoran.common.model.DomainObjectListModel;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.services.PersonService;
import be.superjoran.mint.wicket.BasePage;
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Created by Ghostwritertje
 * Date: 30-Sep-16.
 */
public class PersonListPage extends BasePage<Person> {
    @SpringBean
    private PersonService personService;

    public PersonListPage() {

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        WebMarkupContainer markupContainer = new WebMarkupContainer("markupContainer");

        DataTableBuilderFactory.<Person, String>simple()
                .addColumn(new LambdaColumn<Person, String>(new ResourceModel("name"), Person::getUsername))
                .attach(markupContainer, "datatable", new DomainObjectListModel<>(this.personService));

        this.add(markupContainer);
    }
}
