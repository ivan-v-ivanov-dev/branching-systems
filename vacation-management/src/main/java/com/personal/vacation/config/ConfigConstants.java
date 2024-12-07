package com.personal.vacation.config;

public class ConfigConstants {

    // Mongo Configuration Logger Constants
    private static final String MONGO_CONFIGURATION = "Mongo Configuration :: ";

    private static final String CREATED = " created";

    public static final String MONGO_CLIENT_CREATED = MONGO_CONFIGURATION + "Mongo Client" + CREATED;

    public static final String MONGO_TEMPLATE_CREATED = MONGO_CONFIGURATION + "Mongo Template" + CREATED;

    // Kafka Configuration Logger Constants
    private static final String KAFKA_CONFIGURATION = "Kafka Configuration :: ";

    private static final String FOR_KAFKA_MESSAGING_CREATED = " for Kafka messaging created";

    public static final String DEFAULT_CONSUMER_FACTORY_FOR_KAFKA_MESSAGING_CREATED =
            KAFKA_CONFIGURATION + "Default Consumer Factory" + FOR_KAFKA_MESSAGING_CREATED;

    public static final String CONCURRENT_KAFKA_LISTENER_CONTAINER_FACTORY_FOR_KAFKA_MESSAGING_CREATED =
            KAFKA_CONFIGURATION + "Concurrent Kafka Listener Container Factor" + FOR_KAFKA_MESSAGING_CREATED;

}
