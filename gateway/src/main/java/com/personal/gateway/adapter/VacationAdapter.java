package com.personal.gateway.adapter;

import com.personal.model.dto.VacationGatewayRp;
import com.personal.model.dto.VacationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = VacationResponse.class)
public interface VacationAdapter {

    @Mapping(target = "type", expression = "java(vacationResponse.getType())")
    @Mapping(target = "startDate", expression = "java(vacationResponse.getStartDate())")
    @Mapping(target = "endDate", expression = "java(vacationResponse.getEndDate())")
    @Mapping(target = "submittedOn", expression = "java(vacationResponse.getSubmittedOn())")
    @Mapping(target = "halfDay", expression = "java(vacationResponse.isHalfDay())")
    @Mapping(target = "approved", expression = "java(vacationResponse.isApproved())")
    @Mapping(target = "list", expression = "java(vacationResponse.getList())")
    VacationGatewayRp fromVacationResponseToVacationGatewayRp(VacationResponse vacationResponse);
}
