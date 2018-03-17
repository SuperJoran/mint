package be.superjoran.mint.wicket.statements;

import be.superjoran.mint.domain.Statement;
import be.superjoran.mint.domain.searchresults.StatementSearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilteredStatementsModel implements IModel<List<Statement>> {
    private final IModel<List<Statement>> statementsListModel;
    private final IModel<StatementSearchCriteria> searchModel;

    public FilteredStatementsModel(IModel<List<Statement>> statementsListModel, IModel<StatementSearchCriteria> searchModel) {
        this.statementsListModel = statementsListModel;
        this.searchModel = searchModel;
    }

    @NotNull
    private static Predicate<Statement> filterFuzzy(StatementSearchCriteria searchCriteria) {
        return statement -> {
            String fuzzySearch = searchCriteria.getFuzzySearch();
            return StringUtils.isEmpty(fuzzySearch)
                    || StringUtils.containsIgnoreCase(statement.getDescription(), fuzzySearch)
                    || StringUtils.containsIgnoreCase(statement.getDestinationAccountNumber(), fuzzySearch);
        };
    }

    @NotNull
    private static Predicate<Statement> filterByCategory(StatementSearchCriteria searchCriteria) {
        return statement -> !searchCriteria.getFilterByCategory() || Objects.equals(statement.getCategory(), searchCriteria.getCategory());
    }

    @Override
    public List<Statement> getObject() {
        StatementSearchCriteria searchCriteria = this.searchModel.getObject();
        return this.statementsListModel.getObject().stream()
                .filter(filterFuzzy(searchCriteria))
                .filter(filterByCategory(searchCriteria))
                .collect(Collectors.toList());
    }
}
