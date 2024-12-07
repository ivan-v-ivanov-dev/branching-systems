package com.personal.vacation.service;

import com.personal.model.dto.VacationResponse;
import com.personal.vacation.adapter.VacationAdapter;
import com.personal.vacation.model.Vacation;
import com.personal.vacation.repository.contract.VacationRepository;
import com.personal.vacation.service.contract.VacationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class VacationServiceImpl implements VacationService {

    private final VacationRepository vacationRepository;
    private final VacationAdapter vacationAdapter;

    @Override
    public List<VacationResponse> findUserVacations(String name) {
        List<Vacation> vacations = vacationRepository.findUserVacations(name);
        log.info(format("Retrieve user vacations %s", name));
        return vacations.stream().map(vacationAdapter::fromVacationToVacationResponse).toList();
    }
}
