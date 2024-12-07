package com.personal.vacation.controller;

import com.personal.model.dto.VacationResponse;
import com.personal.vacation.service.contract.VacationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
public class VacationController {

    private final VacationService vacationService;

    @GetMapping("/user/{name}/vacations")
    public List<VacationResponse> findUserVacations(@PathVariable("name") String name) {
        return vacationService.findUserVacations(name);
    }

    @PatchMapping("/vacation/{id}")
    public VacationResponse updateVacation(@PathVariable("id") String id,
                                           @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                           @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                           @RequestParam(value = "halfDay") boolean halfDay) {
        return vacationService.update(id, startDate, endDate, halfDay);
    }

    @DeleteMapping("/vacation/{id}")
    public boolean deleteVacation(@PathVariable("id") String id) {
        return vacationService.delete(id);
    }

    @PatchMapping("/vacation/{id}/approve")
    public VacationResponse approveVacation(@PathVariable("id") String id) {
        return vacationService.approve(id);
    }
}
