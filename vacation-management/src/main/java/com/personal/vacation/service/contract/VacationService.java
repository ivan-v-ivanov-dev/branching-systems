package com.personal.vacation.service.contract;

import com.personal.model.dto.VacationResponse;

import java.time.LocalDate;
import java.util.List;

public interface VacationService {
    List<VacationResponse> findUserVacations(String name);

    VacationResponse updateVacation(String id, LocalDate startDate, LocalDate endDate, boolean halfDay);
}
