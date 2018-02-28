package be.superjoran.mint.batch.categories;

import be.superjoran.mint.domain.Statement;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AssignCategoriesStepConfiguration {

    private final StepBuilderFactory stepBuilderFactory;

    public AssignCategoriesStepConfiguration(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public PostgresPagingQueryProvider queryProvider() {
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("uuid", Order.ASCENDING);

        PostgresPagingQueryProvider queryProvider = new PostgresPagingQueryProvider();
        queryProvider.setSelectClause("select *");
        queryProvider.setFromClause("from T_STATEMENT STATEMENT");
        queryProvider.setWhereClause("WHERE STATEMENT.ORIGINATINGACCOUNT_UUID IN (SELECT BA.UUID\n" +
                "                                            FROM T_BANKACCOUNT BA\n" +
                "                                            WHERE BA.OWNER_UUID = :personUuid)\n" +
                "AND STATEMENT.category_uuid IS NULL");
        queryProvider.setSortKeys(sortKeys);
        return queryProvider;
    }

    @Bean
    @StepScope
    @Autowired
    public ItemReader<Statement> reader(
            DataSource dataSource,
            PagingQueryProvider queryProvider,
            @Value("#{jobParameters['personUuid']}") String personUuid
    ) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("personUuid", personUuid);

        JdbcPagingItemReader<Statement> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setQueryProvider(queryProvider);
        reader.setPageSize(1000);
        reader.setFetchSize(100);
        reader.setRowMapper(new BeanPropertyRowMapper<>(Statement.class));
        reader.setParameterValues(parameters);
        return reader;
    }


    @Bean
    @Autowired
    public Step assignCategoryStep(
            ItemReader<Statement> reader,
            ItemProcessor<Statement, Statement> assignCategoryStatementProcessor,
            ItemWriter<Statement> assignCategoriesItemWriter
    ) {
        return this.stepBuilderFactory.get("assignCategoryStep")
                .<Statement, Statement>chunk(100)
                .reader(reader)
                .processor(assignCategoryStatementProcessor)
                .writer(assignCategoriesItemWriter)
                .build();
    }
}
