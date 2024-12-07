package com.personal.gateway.config.topic;

import com.personal.gateway.config.ConfigConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
@Slf4j
public class NewVacationTopic {

    @Value("${spring.kafka.topic.name.vacation}")
    private String topicName;
    @Value("${spring.kafka.partitions}")
    private String partitions;
    @Value("${spring.kafka.replicas}")
    private String replicas;

    @Bean
    public NewTopic createNewVacationTopic() {
        NewTopic registeredUserTopicForProfileService = TopicBuilder
                .name(topicName)
                .partitions(Integer.parseInt(partitions))
                .replicas(Integer.parseInt(replicas))
                .build();
        log.info(String.format(ConfigConstants.KAFKA_TOPIC_FOR_NEW_VACATION_CREATED_TEMPLATE, topicName));

        return registeredUserTopicForProfileService;
    }
}
