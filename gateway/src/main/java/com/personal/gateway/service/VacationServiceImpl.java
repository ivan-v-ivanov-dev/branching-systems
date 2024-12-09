package com.personal.gateway.service;

import com.personal.gateway.adapter.VacationAdapter;
import com.personal.gateway.service.contract.KafkaMessageSender;
import com.personal.gateway.service.contract.VacationService;
import com.personal.gateway.service.feign.VacationManagementClient;
import com.personal.model.dto.VacationGatewayRp;
import com.personal.model.dto.VacationGatewayRq;
import com.personal.model.dto.VacationResponse;
import com.social.kafka.messages.SickLeaveMessage;
import com.social.kafka.messages.VacationMessage;
import com.social.kafka.messages.contract.KafkaMessage;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static java.lang.String.format;

@Service
@Slf4j
public class VacationServiceImpl implements VacationService {

    private final VacationManagementClient vacationManagementClient;
    private final VacationAdapter vacationAdapter;
    private final KafkaMessageSender kafkaMessageSender;
    private final String createVacationTopic;
    private final String sickLeaveTopic;

    public VacationServiceImpl(VacationManagementClient vacationManagementClient,
                               VacationAdapter vacationAdapter,
                               KafkaMessageSender kafkaMessageSender,
                               @Value("${spring.kafka.topic.name.vacation}") String createVacationTopic,
                               @Value("${spring.kafka.topic.name.sick.leave}") String sickLeaveTopic) {
        this.vacationManagementClient = vacationManagementClient;
        this.vacationAdapter = vacationAdapter;
        this.kafkaMessageSender = kafkaMessageSender;
        this.createVacationTopic = createVacationTopic;
        this.sickLeaveTopic = sickLeaveTopic;
    }

    @Override
    public List<VacationGatewayRp> findUserVacations(String name) {
        try {
            List<VacationResponse> vacationResponses = vacationManagementClient.findUserVacations(name);
            log.info(format("Retrieve user %s vacations", name));
            return vacationResponses.stream().map(vacationAdapter::fromVacationResponseToVacationGatewayRp).toList();
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Vacation Service is down");
        }
    }

    @Override
    public void create(VacationGatewayRq vacationGatewayRq) {
        KafkaMessage createVacationMessage = VacationMessage.builder()
                .applicant(vacationGatewayRq.getApplicant())
                .type(vacationGatewayRq.getType())
                .startDate(vacationGatewayRq.getStartDate())
                .endDate(vacationGatewayRq.getEndDate())
                .submittedOn(LocalDate.now().toString())
                .halfDay(vacationGatewayRq.isHalfDay())
                .approved(false).build();

        kafkaMessageSender.send(createVacationMessage, createVacationTopic);
        log.info(format("New vacation request sent in %s topic", createVacationTopic));
    }

    @Override
    public void updateSickList(String id, MultipartFile file) {
        try {
            KafkaMessage sickLeaveMessage = SickLeaveMessage.builder()
                    .vacationId(id)
                    .file("data:image/png;base64, " + Base64.getEncoder().encodeToString(file.getBytes())).build();

            kafkaMessageSender.send(sickLeaveMessage, sickLeaveTopic);
            log.info(format("New sick leave document sent in %s topic", sickLeaveTopic));
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
        }
    }

    @Override
    public VacationGatewayRp update(String id, String startDate, String endDate, boolean halfDay) {
        try {
            VacationResponse vacationResponse = vacationManagementClient.updateVacation(id, startDate, endDate, halfDay);
            log.info(format("Update vacation %s", id));
            return vacationAdapter.fromVacationResponseToVacationGatewayRp(vacationResponse);
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Vacation Service is down");
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            boolean isDeleted = vacationManagementClient.deleteVacation(id);
            if (isDeleted) {
                log.info(format("Vacation %s is deleted", id));
                return isDeleted;
            }
            log.info(format("Vacation %s is not deleted", id));
            return isDeleted;
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Vacation Service is down");
        }
    }

    @Override
    public VacationGatewayRp approve(String id) {
        try {
            VacationResponse vacationResponse = vacationManagementClient.approveVacation(id);
            log.info(format("Vacation %s is approved", id));
            return vacationAdapter.fromVacationResponseToVacationGatewayRp(vacationResponse);
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Vacation Service is down");
        }
    }
}
