package com.personal.gateway.adapter;

import com.personal.model.dto.ProjectGatewayRp;
import com.personal.model.dto.ProjectResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProjectResponse.class)
public interface ProjectAdapter {

    @Mapping(target = "name", expression = "java(projectResponse.getName())")
    @Mapping(target = "description", expression = "java(projectResponse.getDescription())")
    ProjectGatewayRp fromProjectResponseToProjectGatewayRp(ProjectResponse projectResponse);
}
