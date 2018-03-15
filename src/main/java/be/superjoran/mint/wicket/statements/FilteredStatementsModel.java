package be.superjoran.mint.wicket.statements;

import be.superjoran.mint.domain.Statement;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.model.IModel;

import java.util.List;
import java.util.stream.Collectors;

public class FilteredStatementsModel implements IModel<List<Statement>> {
    private final IModel<List<Statement>> statementsListModel;
    private final IModel<String> searchModel;

    public FilteredStatementsModel(IModel<List<Statement>> statementsListModel, IModel<String> searchModel) {
        this.statementsListModel = statementsListModel;
        this.searchModel = searchModel;
    }

    @Override
    public List<Statement> getObject() {
        return this.statementsListModel.getObject().stream()
                .filter(statement -> StringUtils.isEmpty(this.searchModel.getObject())
                        || StringUtils.containsIgnoreCase(statement.getDescription(), this.searchModel.getObject())
                        || StringUtils.containsIgnoreCase(statement.getDestinationAccountNumber(), this.searchModel.getObject())
                )
                .collect(Collectors.toList());
    }
}
