package com.personal.vacation.service.contract;

import com.personal.model.dto.VacationResponse;
import com.personal.vacation.model.Vacation;

import java.util.List;

public interface VacationService {
    List<VacationResponse> findUserVacations(String name);

    void create(Vacation vacation);

    void updateSickLeave(String vacationId, String file);

    VacationResponse update(String id, String startDate, String endDate, boolean halfDay);

    boolean delete(String id);

    VacationResponse approve(String id);
}
