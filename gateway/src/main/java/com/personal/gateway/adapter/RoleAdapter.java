package com.personal.gateway.adapter;

import com.personal.model.dto.RoleGatewayRp;
import com.personal.model.dto.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RoleResponse.class)
public interface RoleAdapter {

    @Mapping(target = "name", expression = "java(response.getName())")
    @Mapping(target = "userCount", expression = "java(response.getUserCount())")
    RoleGatewayRp fromRoleResponseToRoleGatewayResponse(RoleResponse response);
}
