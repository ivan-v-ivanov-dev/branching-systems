package com.personal.user.adapter;

import com.personal.model.model.UserResponse;
import com.personal.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAdapter {
    @Mapping(source = "role.name", target = "role")
    UserResponse fromUserToUserResponse(User user);
}
