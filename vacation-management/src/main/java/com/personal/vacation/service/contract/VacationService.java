package com.personal.vacation.service.contract;

import com.personal.model.dto.VacationResponse;

import java.util.List;

public interface VacationService {
    List<VacationResponse> findUserVacations(String name);
}
