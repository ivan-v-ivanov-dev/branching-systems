package com.personal.vacation.repository.contract;

import com.personal.vacation.model.Vacation;

import java.util.List;

public interface VacationRepository {
    List<Vacation> findUserVacations(String applicant);
}
