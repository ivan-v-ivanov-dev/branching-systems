package com.personal.gateway.service;

import com.personal.gateway.adapter.VacationAdapter;
import com.personal.gateway.service.contract.KafkaMessageSender;
import com.personal.gateway.service.contract.VacationService;
import com.personal.gateway.service.feign.VacationManagementClient;
import com.personal.model.dto.VacationGatewayRp;
import com.personal.model.dto.VacationGatewayRq;
import com.personal.model.dto.VacationResponse;
import com.social.kafka.messages.VacationMessage;
import com.social.kafka.messages.contract.KafkaMessage;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Slf4j
public class VacationServiceImpl implements VacationService {

    private final VacationManagementClient vacationManagementClient;
    private final VacationAdapter vacationAdapter;
    private final KafkaMessageSender kafkaMessageSender;
    @Value("${spring.kafka.topic.name.vacation}")
    private final String createVacationTopic;

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
    public void create(VacationGatewayRq vacationGatewayRq, MultipartFile list) {
        KafkaMessage createVacationMessage = VacationMessage.builder()
                .applicant(vacationGatewayRq.getApplicant())
                .type(vacationGatewayRq.getType())
                .startDate(vacationGatewayRq.getStartDate())
                .endDate(vacationGatewayRq.getEndDate())
                .submittedOn(LocalDate.now().toString())
                .halfDay(vacationGatewayRq.isHalfDay())
                .approved(false)
                .list(list.getContentType().getBytes().toString()).build();

        kafkaMessageSender.send(createVacationMessage, createVacationTopic);
        log.info(format("New vacation request sent in %s topic", createVacationTopic));
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
