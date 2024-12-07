package com.personal.gateway.service.contract;

import com.social.kafka.messages.contract.KafkaMessage;

public interface KafkaMessageSender {
    void send(KafkaMessage kafkaMessage, String topic);
}
