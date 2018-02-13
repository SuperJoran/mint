package be.superjoran.mint.dao.config.datasource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DevelopmentDataSource {
    private static final Logger LOG = LogManager.getLogger();
    @Bean
//    @Profile("local")
    public DataSource localDataSource(
            @Value("${application.db.owner.driver}") String driverClassname,
            @Value("${application.db.owner.user}") String username,
            @Value("${application.db.owner.password}") String password,
            @Value("${application.db.owner.url}") String url
    ) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassname);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setSchema(username);
        dataSource.setPassword(password);

        LOG.info("Using postgres local DataSource");
        return dataSource;
    }

    @Bean
    public Properties jpaProperties(
            @Value("${application.db.owner.url}") String url
    ) {
        Properties properties = new Properties();
        properties.setProperty("connection.pool_size", "10");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
        properties.setProperty("hibernate.current_session_context_class", "org.hibernate.context.internal.ThreadLocalSessionContext");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.connection.url", url);
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        LOG.info("DataSource location : {}", properties.getProperty("hibernate.connection.url"));
        return properties;
    }
}
