package be.superjoran.mint.batch.categories;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssignCategoriesJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;

    public AssignCategoriesJobConfiguration(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public static StepScope scope() {
        return new StepScope();
    }

    @Bean
    @Autowired
    public Job assignCategoriesJob(Step assignCategoryStep) {
        return this.jobBuilderFactory.get("eventRenamingJob")
                .incrementer(new RunIdIncrementer())
                .start(assignCategoryStep)
                .build();
    }
}
