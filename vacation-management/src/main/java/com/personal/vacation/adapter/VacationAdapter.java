package com.personal.vacation.adapter;

import com.personal.model.dto.VacationResponse;
import com.personal.vacation.model.Vacation;
import com.social.kafka.messages.VacationMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = Vacation.class)
public interface VacationAdapter {

    @Mapping(target = "type", expression = "java(vacation.getType())")
    @Mapping(target = "startDate", expression = "java(vacation.getStartDate())")
    @Mapping(target = "endDate", expression = "java(vacation.getEndDate())")
    @Mapping(target = "submittedOn", expression = "java(vacation.getSubmittedOn())")
    @Mapping(target = "halfDay", expression = "java(vacation.isHalfDay())")
    @Mapping(target = "approved", expression = "java(vacation.isApproved())")
    @Mapping(target = "list", expression = "java(vacation.getList())")
    VacationResponse fromVacationToVacationResponse(Vacation vacation);

    @Mapping(target = "type", expression = "java(vacation.getType())")
    @Mapping(target = "applicant", expression = "java(vacation.getApplicant())")
    @Mapping(target = "startDate", expression = "java(vacation.getStartDate())")
    @Mapping(target = "endDate", expression = "java(vacation.getEndDate())")
    @Mapping(target = "submittedOn", expression = "java(vacation.getSubmittedOn())")
    @Mapping(target = "halfDay", expression = "java(vacation.isHalfDay())")
    @Mapping(target = "approved", expression = "java(vacation.isApproved())")
    @Mapping(target = "list", expression = "java(vacation.getList())")
    Vacation fromVacationMessageToVacation(VacationMessage vacationMessage);
}
