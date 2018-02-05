package be.superjoran.mint.wicket.configuration;

import be.superjoran.mint.dao.config.FlywayMigrate;
import be.superjoran.mint.dao.config.PersistenceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {
        "classpath:application.properties",
        "classpath:application/${application.config}/application.properties"
})
@Configuration
@Import(PersistenceConfig.class)
public class ApplicationConfig {
}
