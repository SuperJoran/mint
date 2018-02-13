package be.superjoran.mint.batch.categories;

import be.superjoran.mint.domain.Statement;
import be.superjoran.mint.services.StatementService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssignCategoriesItemWriter implements ItemWriter<Statement> {
    private final StatementService statementService;

    public AssignCategoriesItemWriter(StatementService statementService) {
        this.statementService = statementService;
    }

    @Override
    public void write(List<? extends Statement> items) throws Exception {
        this.statementService.save(items.stream().map(i -> (Statement) i).collect(Collectors.toList()));
    }
}
