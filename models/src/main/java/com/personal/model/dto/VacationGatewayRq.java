package com.personal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class VacationGatewayRq {

    @NotBlank(message = "Applicant can't be blank")
    @NotEmpty(message = "Applicant can't be empty")
    private String applicant;

    @NotBlank(message = "Type can't be blank")
    @NotEmpty(message = "Type can't be empty")
    private String type;

    @NotBlank(message = "Start date can't be blank")
    @NotEmpty(message = "Start date can't be empty")
    private String startDate;

    @NotBlank(message = "End date can't be blank")
    @NotEmpty(message = "End date can't be empty")
    private String endDate;

    @NotBlank(message = "Half day can't be blank")
    @NotEmpty(message = "Half day can't be empty")
    private boolean halfDay;
}
