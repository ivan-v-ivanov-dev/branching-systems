package com.personal.vacation.service;

import com.personal.vacation.adapter.VacationAdapter;
import com.personal.vacation.service.contract.VacationService;
import com.social.kafka.messages.SickLeaveMessage;
import com.social.kafka.messages.VacationMessage;
import com.social.kafka.messages.contract.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class VacationListener {

    private final VacationService vacationService;
    private final VacationAdapter vacationAdapter;


    @KafkaListener(topics = "${spring.kafka.topic.name.vacation}", groupId = "${spring.kafka.group.id}")
    public void listener(KafkaMessage kafkaMessage) {
        VacationMessage vacationMessage = (VacationMessage) kafkaMessage;
        log.info("New vacation message received");
        vacationService.create(vacationAdapter.fromVacationMessageToVacation(vacationMessage));
    }

    @KafkaListener(topics = "${spring.kafka.topic.name.sick.leave}", groupId = "${spring.kafka.group.id}")
    public void sickLeaveListener(KafkaMessage kafkaMessage) {
        SickLeaveMessage sickLeaveMessage = (SickLeaveMessage) kafkaMessage;
        log.info("New sick leave message received");
        vacationService.updateSickLeave(sickLeaveMessage.getVacationId(), sickLeaveMessage.getFile());
    }

}
