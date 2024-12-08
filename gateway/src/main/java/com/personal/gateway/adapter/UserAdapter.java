package com.personal.gateway.adapter;

import com.personal.model.dto.UserGatewayRp;
import com.personal.model.dto.UserGatewayRq;
import com.personal.model.dto.UserRequest;
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

    @Mapping(target = "username", expression = "java(userGatewayRq.getUsername())")
    @Mapping(target = "firstName", expression = "java(userGatewayRq.getFirstName())")
    @Mapping(target = "lastName", expression = "java(userGatewayRq.getLastName())")
    @Mapping(target = "role", expression = "java(userGatewayRq.getRole())")
    UserRequest fromUserGatewayRqToUserRequest(UserGatewayRq userGatewayRq);
}
