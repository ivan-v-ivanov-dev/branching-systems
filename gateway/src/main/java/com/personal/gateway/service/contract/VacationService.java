package com.personal.gateway.service.contract;

import com.personal.model.dto.VacationGatewayRp;

import java.util.List;

public interface VacationService {
    List<VacationGatewayRp> findUserVacations(String name);

    VacationGatewayRp update(String id, String startDate, String endDate, boolean halfDay);
}
