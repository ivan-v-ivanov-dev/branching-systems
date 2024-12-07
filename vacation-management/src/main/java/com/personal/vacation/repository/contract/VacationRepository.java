package com.personal.vacation.repository.contract;

import com.personal.vacation.model.Vacation;

import java.time.LocalDate;
import java.util.List;

public interface VacationRepository {
    List<Vacation> findUserVacations(String applicant);

    void create(Vacation vacation);

    void update(String id, LocalDate startDate, LocalDate endDate, boolean halfDay);

    Vacation findById(String id);

    long delete(String id);

    void approve(String id);
}
