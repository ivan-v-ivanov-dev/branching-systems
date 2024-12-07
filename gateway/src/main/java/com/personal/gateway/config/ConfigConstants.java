package com.personal.gateway.config;

public class ConfigConstants {

    // Kafka Configurations
    private static final String KAFKA_CONFIGURATIONS = "Kafka Configurations :: ";

    private static final String FOR_KAFKA_MESSAGING_CREATED = " for Kafka messaging created";

    public static final String DEFAULT_PRODUCER_FACTORY_FOR_KAFKA_MESSAGING_CREATED =
            KAFKA_CONFIGURATIONS + "Default Producer Factory" + FOR_KAFKA_MESSAGING_CREATED;

    public static final String KAFKA_TEMPLATE_FOR_KAFKA_MESSAGING_CREATED =
            KAFKA_CONFIGURATIONS + "Kafka Template" + FOR_KAFKA_MESSAGING_CREATED;

    public static final String DEFAULT_CONSUMER_FACTORY_FOR_KAFKA_MESSAGING_CREATED =
            KAFKA_CONFIGURATIONS + "Default Consumer Factory" + FOR_KAFKA_MESSAGING_CREATED;

    public static final String CONCURRENT_KAFKA_LISTENER_CONTAINER_FACTORY_FOR_KAFKA_MESSAGING_CREATED =
            KAFKA_CONFIGURATIONS + "Concurrent Kafka Listener Container Factor" + FOR_KAFKA_MESSAGING_CREATED;

    // Kafka Topic constants
    private static final String KAFKA_TOPIC_NEW_TOPIC_FOR = "Kafka Topic :: New topic for ";

    private static final String CREATED_TOPIC_NAME = " created (name:  %s)";

    public static final String KAFKA_TOPIC_FOR_NEW_VACATION_CREATED_TEMPLATE =
            KAFKA_TOPIC_NEW_TOPIC_FOR + "new vacation" + CREATED_TOPIC_NAME;

    private ConfigConstants() {
    }
}
