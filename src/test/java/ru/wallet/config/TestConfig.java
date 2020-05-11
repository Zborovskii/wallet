package ru.wallet.config;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.wallet.repository")
@EntityScan(basePackages = "ru.wallet.domain")
@ActiveProfiles({"test"})
public class TestConfig {

    private static HikariDataSource buildHikariDataSource() {
        return (HikariDataSource) DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean("walletDataSource")
    @Primary
    @ConfigurationProperties(value = "datasource.wallet")
    public HikariDataSource tasksExtensionsDataSource() {
        HikariDataSource hikariDataSource = buildHikariDataSource();
        hikariDataSource.setMaximumPoolSize(2);
        return hikariDataSource;
    }

    @Bean
    public SpringLiquibase liquibase(@Qualifier("walletDataSource") HikariDataSource dataSource,
                                     LiquibaseProperties liquibaseProperties) {

        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setShouldRun(true);
        return liquibase;
    }
}
