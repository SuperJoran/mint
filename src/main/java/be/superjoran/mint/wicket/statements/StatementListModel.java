package be.superjoran.mint.wicket.statements;

import be.superjoran.common.model.LoadableListModel;
import be.superjoran.mint.domain.Person;
import be.superjoran.mint.domain.Statement;
import be.superjoran.mint.services.StatementService;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class StatementListModel extends LoadableListModel<Statement> {

    @SpringBean
    private StatementService statementService;

    private final IModel<Person> personIModel;

    public StatementListModel(IModel<Person> personIModel) {
        this.personIModel = personIModel;
    }

    @Override
    protected List<Statement> load() {
        return this.statementService.findAllByOwner(this.personIModel.getObject());
    }
}
