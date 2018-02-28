package be.superjoran.mint.batch.categories;

import be.superjoran.mint.domain.Category;
import be.superjoran.mint.domain.Statement;
import be.superjoran.mint.domain.searchresults.DestinationCategory;
import be.superjoran.mint.services.DestinationCategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AssignCategoryStatementProcessor implements ItemProcessor<Statement, Statement> {
    private static final Logger LOG = LogManager.getLogger();

    private final DestinationCategoryService destinationCategoryService;
    private Map<String, DestinationCategory> destinationAccountToCategoryMap;

    public AssignCategoryStatementProcessor(DestinationCategoryService destinationCategoryService) {
        this.destinationCategoryService = destinationCategoryService;
    }

    @PostConstruct
    public void init() {
        this.destinationAccountToCategoryMap = this.destinationCategoryService.findDestinationCategories()
                .stream()
                .collect(Collectors.toMap(
                        DestinationCategory::getDestinationAccountNumber,
                        Function.identity()
                ));
    }

    @Override
    public Statement process(Statement statement) {
        LOG.debug(() -> String.format("Processing statement with uuid %s", statement.getUuid()));

        if (this.destinationAccountToCategoryMap.containsKey(statement.getDestinationAccountNumber())) {
            Category category = this.destinationAccountToCategoryMap.get(statement.getDestinationAccountNumber()).getCategory();
            LOG.debug(() -> String.format("Assigning category %s to statement with uuid %s ", category.getName(), statement.getUuid()));
            statement.setCategory(category);
        }
        return statement;
    }

}
