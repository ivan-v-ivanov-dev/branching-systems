package com.personal.vacation.repository.contract;

import com.personal.model.dto.VacationResponse;
import com.personal.vacation.model.Vacation;

import java.time.LocalDate;
import java.util.List;

public interface VacationRepository {
    List<Vacation> findUserVacations(String applicant);

    void updateVacation(String id, LocalDate startDate, LocalDate endDate, boolean halfDay);

    Vacation findById(String id);
}
