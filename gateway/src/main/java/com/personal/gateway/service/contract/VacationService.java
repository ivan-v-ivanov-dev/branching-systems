package com.personal.gateway.service.contract;

import com.personal.model.dto.VacationGatewayRp;
import com.personal.model.dto.VacationGatewayRq;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VacationService {
    List<VacationGatewayRp> findUserVacations(String name);

    void create(VacationGatewayRq vacationGatewayRq);

    void updateSickList(String id, MultipartFile file);

    VacationGatewayRp update(String id, String startDate, String endDate, boolean halfDay);

    boolean delete(String id);

    VacationGatewayRp approve(String id);
}
