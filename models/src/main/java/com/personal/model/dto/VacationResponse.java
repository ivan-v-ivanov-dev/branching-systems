package com.personal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class VacationResponse {

    private String type;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate submittedOn;

    private boolean halfDay;

    private boolean approved;

    private String list;
}
