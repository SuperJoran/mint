package be.superjoran.mint.wicket.configuration;

import be.superjoran.mint.dao.config.PersistenceConfig;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {
        "classpath:application.properties",
        "classpath:application/${application.config}/application.properties"
})
@Configuration
@ComponentScan(value = {"be.superjoran.mint.services", "be.superjoran.mint.batch"})
@Import(PersistenceConfig.class)
@EnableBatchProcessing
public class ApplicationConfig {
}
