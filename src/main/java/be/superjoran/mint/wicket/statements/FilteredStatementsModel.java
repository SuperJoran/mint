package be.superjoran.mint.wicket.statements;

import be.superjoran.mint.domain.Statement;
import be.superjoran.mint.domain.searchresults.StatementSearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.model.IModel;

import java.util.List;
import java.util.stream.Collectors;

public class FilteredStatementsModel implements IModel<List<Statement>> {
    private final IModel<List<Statement>> statementsListModel;
    private final IModel<StatementSearchCriteria> searchModel;

    public FilteredStatementsModel(IModel<List<Statement>> statementsListModel, IModel<StatementSearchCriteria> searchModel) {
        this.statementsListModel = statementsListModel;
        this.searchModel = searchModel;
    }

    @Override
    public List<Statement> getObject() {
        return this.statementsListModel.getObject().stream()
                .filter(statement -> {
                            String fuzzySearch = this.searchModel.getObject().getFuzzySearch();
                            return StringUtils.isEmpty(fuzzySearch)
                                    || StringUtils.containsIgnoreCase(statement.getDescription(), fuzzySearch)
                                    || StringUtils.containsIgnoreCase(statement.getDestinationAccountNumber(), fuzzySearch);
                        }
                )
                .collect(Collectors.toList());
    }
}
