package com.personal.gateway.config;

import com.social.kafka.messages.contract.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@Slf4j
public class KafkaConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String producerServer;
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String consumerServer;
    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String consumerAutoOffsetReset;
    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private String consumerJsonTrustedPackages;
    @Value("${spring.kafka.consumer.key.type.packages}")
    private String consumerKeyTypePackages;
    @Value("${spring.kafka.consumer.value.type.packages}")
    private String consumerValueTypePackages;

    @Bean
    public ProducerFactory<String, KafkaMessage> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.producerServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        DefaultKafkaProducerFactory<String, KafkaMessage> defaultKafkaProducerFactory = new DefaultKafkaProducerFactory<>(props);
        log.info(ConfigConstants.DEFAULT_PRODUCER_FACTORY_FOR_KAFKA_MESSAGING_CREATED);

        return defaultKafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, KafkaMessage> kafkaTemplate() {
        KafkaTemplate<String, KafkaMessage> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        log.info(ConfigConstants.KAFKA_TEMPLATE_FOR_KAFKA_MESSAGING_CREATED);

        return kafkaTemplate;
    }

    @Bean
    public ConsumerFactory<String, KafkaMessage> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.consumerServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, this.consumerGroupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, this.consumerAutoOffsetReset);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, this.consumerJsonTrustedPackages);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.KEY_DEFAULT_TYPE, this.consumerKeyTypePackages);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, this.consumerValueTypePackages);

        DefaultKafkaConsumerFactory<String, KafkaMessage> kafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(props);
        log.info(ConfigConstants.DEFAULT_CONSUMER_FACTORY_FOR_KAFKA_MESSAGING_CREATED);

        return kafkaConsumerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        log.info(ConfigConstants.CONCURRENT_KAFKA_LISTENER_CONTAINER_FACTORY_FOR_KAFKA_MESSAGING_CREATED);

        return factory;
    }
}