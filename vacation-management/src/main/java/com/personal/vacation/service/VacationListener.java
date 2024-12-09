package com.personal.vacation.service;

import com.personal.vacation.adapter.VacationAdapter;
import com.personal.vacation.service.contract.VacationService;
import com.social.kafka.messages.VacationMessage;
import com.social.kafka.messages.contract.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VacationListener {

    public VacationListener(VacationService vacationService,
                            VacationAdapter vacationAdapter,
                            @Value("${spring.kafka.topic.name.vacation}") String newVacationTopic) {
        this.vacationService = vacationService;
        this.vacationAdapter = vacationAdapter;
        this.newVacationTopic = newVacationTopic;
    }

    private final VacationService vacationService;
    private final VacationAdapter vacationAdapter;
    private final String newVacationTopic;

    @KafkaListener(topics = "${spring.kafka.topic.name.vacation}", groupId = "${spring.kafka.group.id}")
    public void listener(KafkaMessage kafkaMessage) {
        VacationMessage vacationMessage = (VacationMessage) kafkaMessage;
        log.info("New vacation message received");
        vacationService.create(vacationAdapter.fromVacationMessageToVacation(vacationMessage));
    }

}
