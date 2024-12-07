package com.personal.vacation.service;

import com.personal.vacation.adapter.VacationAdapter;
import com.personal.vacation.service.contract.VacationService;
import com.social.kafka.messages.VacationMessage;
import com.social.kafka.messages.contract.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Slf4j
public class VacationListener {

    private final VacationService vacationService;
    private final VacationAdapter vacationAdapter;
    @Value("${spring.kafka.topic.name.vacation}")
    String newVacationTopic;

    @KafkaListener(topics = "${spring.kafka.topic.name.vacation}", groupId = "${spring.kafka.group.id}")
    public void listener(KafkaMessage kafkaMessage) {
        VacationMessage vacationMessage = (VacationMessage) kafkaMessage;
        log.info("New vacation message received");
        vacationService.create(vacationAdapter.fromVacationMessageToVacation(vacationMessage));
    }

}
