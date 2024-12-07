package com.personal.vacation.service;

import com.personal.model.dto.VacationResponse;
import com.personal.vacation.adapter.VacationAdapter;
import com.personal.vacation.model.Vacation;
import com.personal.vacation.repository.contract.VacationRepository;
import com.personal.vacation.service.contract.VacationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public void create(Vacation vacation) {
        vacationRepository.create(vacation);
        log.info("New vacation created");
    }

    @Override
    public VacationResponse update(String id, LocalDate startDate, LocalDate endDate, boolean halfDay) {
        vacationRepository.update(id, startDate, endDate, halfDay);
        log.info(format("Vacation %s updated", id));
        return vacationAdapter.fromVacationToVacationResponse(vacationRepository.findById(id));
    }

    @Override
    public boolean delete(String id) {
        long deletedCount = vacationRepository.delete(id);
        if (deletedCount > 0) {
            log.info(format("Delete vacations %s", id));
            return true;
        }
        log.info(format("Vacation with id %s is not deleted", id));
        return false;
    }

    @Override
    public VacationResponse approve(String id) {
        vacationRepository.approve(id);
        log.info(format("Vacation %s approved", id));
        return vacationAdapter.fromVacationToVacationResponse(vacationRepository.findById(id));
    }
}
