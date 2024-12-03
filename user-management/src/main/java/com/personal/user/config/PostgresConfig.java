package com.personal.user.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.personal.project.repository",
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager")
@Slf4j
public class PostgresConfig {

    @Value("${spring.datasource-postgres.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource-postgres.url}")
    private String dataBaseUrl;
    @Value("${spring.datasource-postgres.username}")
    private String dataBaseUsername;
    @Value("${spring.datasource-postgres.password}")
    private String dataBasePassword;

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName(ConfigConstants.POSTGRES_CONNECTION_POOL);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(dataBaseUrl);
        hikariConfig.setUsername(dataBaseUsername);
        hikariConfig.setPassword(dataBasePassword);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        log.info(ConfigConstants.POSTGRES_CONFIGURATION_HIKARI_DATA_SOURCE_CREATED);
        return hikariDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setPackagesToScan("com.personal.project.model");
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        factory.setJpaVendorAdapter(adapter);
        log.info(ConfigConstants.POSTGRES_CONFIGURATION_LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY);
        return factory;
    }

    @Bean
    public PlatformTransactionManager postgresTransactionManager(LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(postgresEntityManagerFactory.getObject());
        log.info(ConfigConstants.POSTGRES_CONFIGURATION_JPA_TRANSACTION_MANAGER_CREATED);
        return manager;
    }
}
