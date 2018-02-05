package be.superjoran.mint.dao.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

}
