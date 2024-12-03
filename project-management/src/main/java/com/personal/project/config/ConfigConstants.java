package com.personal.project.config;

public class ConfigConstants {

    // Postgres Configuration Logger Constants
    public static final String POSTGRES_CONNECTION_POOL = "Postgres Connection Pool";
    public static final String POSTGRES_CONFIGURATION = "Postgres Configuration :: ";
    public static final String POSTGRES_CONFIGURATION_HIKARI_DATA_SOURCE_CREATED = POSTGRES_CONFIGURATION + "Hikari DataSource Created";
    public static final String POSTGRES_CONFIGURATION_LOCAL_CONTAINER_ENTITY_MANAGER_FACTORY = POSTGRES_CONFIGURATION + "LocalContainerEntityManagerFactory Created";
    public static final String POSTGRES_CONFIGURATION_JPA_TRANSACTION_MANAGER_CREATED = POSTGRES_CONFIGURATION + "JpaTransactionManager Created";

        private ConfigConstants() {
        }
}
