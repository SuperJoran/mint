package be.superjoran.mint.dao.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@PropertySource(value = {
        "classpath:application.properties",
        "classpath:application/${application.config}/application.properties"
})
@EnableJpaRepositories({"be.superjoran.mint.dao"})
@Import({FlywayMigrate.class})
@ComponentScan(value = {"be.superjoran.mint.dao.custom"})
@Configuration
public class PersistenceTestConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Autowired
    public JpaTransactionManager transactionManager(DataSource dataSource) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(dataSource);
        return jpaTransactionManager;
    }

    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Properties jpaProperties) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("be.superjoran.mint.domain");
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setJpaProperties(jpaProperties);

        return entityManagerFactory;
    }

    @Bean
    public Flyway flyway(DataSource localTestDataSource) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(localTestDataSource);
        flyway.setBaselineOnMigrate(true);
        return flyway;
    }

    @Bean
//    @Profile("local")
    public DataSource localTestDataSource(
            @Value("${application.db.owner.test.driver}") String driverClassname,
            @Value("${application.db.owner.test.user}") String username,
            @Value("${application.db.owner.test.password}") String password,
            @Value("${application.db.owner.test.url}") String url
    ) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassname);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setSchema(username);
        dataSource.setPassword(password);

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
        return properties;
    }
}


