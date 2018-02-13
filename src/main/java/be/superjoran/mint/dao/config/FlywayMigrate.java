package be.superjoran.mint.dao.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * Created by Jorandeboever
 * Date: 22-Dec-16.
 */
@Import({Flyway.class})
@Configuration
public class FlywayMigrate {

    @Bean
    @Autowired
    public Flyway flywayMigrate (Flyway flyway, DataSource dataSource) {
        flyway.setBaselineOnMigrate(true);
        flyway.setDataSource(dataSource);
        flyway.setOutOfOrder(true);
        flyway.migrate();

        return flyway;
    }
}
