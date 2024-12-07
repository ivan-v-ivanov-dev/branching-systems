package com.personal.vacation.controller;

import com.personal.model.dto.VacationResponse;
import com.personal.vacation.service.contract.VacationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class VacationController {

    private final VacationService vacationService;

    @GetMapping("/user/{name}/vacations")
    public List<VacationResponse> findUserVacations(@PathVariable("name") String name) {
        return vacationService.findUserVacations(name);
    }
}
