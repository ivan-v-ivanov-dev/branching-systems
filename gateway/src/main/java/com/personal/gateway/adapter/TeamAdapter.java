package com.personal.gateway.adapter;

import com.personal.model.dto.ProjectGatewayRp;
import com.personal.model.dto.ProjectResponse;
import com.personal.model.dto.TeamGatewayRp;
import com.personal.model.dto.TeamResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamAdapter {

    @Mapping(target = "name", expression = "java(teamResponse.getName())")
    @Mapping(target = "projects", expression = "java(teamResponse.getProjects().stream().map(project -> fromProjectResponseToProjectGatewayResponse(project)).collect(java.util.stream.Collectors.toSet()))")
    TeamGatewayRp fromTeamResponseToTeamGatewayRp(TeamResponse teamResponse);

    @Mapping(target = "name", expression = "java(projectResponse.getName())")
    @Mapping(target = "description", expression = "java(projectResponse.getDescription())")
    ProjectGatewayRp fromProjectResponseToProjectGatewayResponse(ProjectResponse projectResponse);
}
