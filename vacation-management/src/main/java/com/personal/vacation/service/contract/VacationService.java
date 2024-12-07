package com.personal.vacation.service.contract;

import com.personal.model.dto.VacationResponse;
import com.personal.vacation.model.Vacation;

import java.time.LocalDate;
import java.util.List;

public interface VacationService {
    List<VacationResponse> findUserVacations(String name);

    void create(Vacation vacation);

    VacationResponse update(String id, LocalDate startDate, LocalDate endDate, boolean halfDay);

    boolean delete(String id);

    VacationResponse approve(String id);
}
