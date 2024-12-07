package com.personal.gateway.adapter;

import com.personal.model.dto.UserGatewayRp;
import com.personal.model.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserResponse.class)
public interface UserAdapter {

    @Mapping(target = "username", expression = "java(response.getUsername())")
    @Mapping(target = "firstName", expression = "java(response.getFirstName())")
    @Mapping(target = "lastName", expression = "java(response.getLastName())")
    @Mapping(target = "role", expression = "java(response.getRole())")
    UserGatewayRp fromUserResponseToUserGatewayRp(UserResponse response);
}
