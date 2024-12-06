package com.personal.project.adapter;

import com.personal.project.model.Role;
import com.personal.model.dto.UserResponse;
import com.personal.project.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = Role.class)
public interface UserAdapter {
    @Mapping(target = "username", expression = "java(user.getUsername())")
    @Mapping(target = "firstName", expression = "java(user.getFirstName())")
    @Mapping(target = "lastName", expression = "java(user.getLastName())")
    @Mapping(target = "team", expression = "java(user.getTeam())")
    @Mapping(target = "role", expression = "java(user.getRole().getName())")
    UserResponse fromUserToUserResponse(User user);
}
