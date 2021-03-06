package be.superjoran.mint.wicket.configuration;

import be.superjoran.mint.dao.config.PersistenceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import javax.validation.Validation;
import javax.validation.Validator;

@PropertySource(value = {
        "classpath:application.properties",
        "classpath:application/${application.config}/application.properties"
})
@Configuration
@ComponentScan(value = {"be.superjoran.mint.services", "be.superjoran.mint.batch"})
@Import(PersistenceConfig.class)
public class ApplicationConfig {

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
