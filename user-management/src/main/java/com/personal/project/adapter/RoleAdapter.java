package com.personal.project.adapter;

import com.personal.model.dto.RoleResponse;
import com.personal.project.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = Role.class)
public interface RoleAdapter {

    @Mapping(target = "name", expression = "java(role.getName())")
    RoleResponse fromRoleToRoleResponse(Role role);
}
