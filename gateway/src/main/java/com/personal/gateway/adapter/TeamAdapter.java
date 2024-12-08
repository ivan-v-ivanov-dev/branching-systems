package com.personal.gateway.adapter;

import com.personal.model.dto.*;
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

    @Mapping(target = "name", expression = "java(teamGatewayRq.getName())")
    @Mapping(target = "projectId", expression = "java(teamGatewayRq.getProjectId())")
    @Mapping(target = "leaderId", expression = "java(teamGatewayRq.getLeaderId())")
    TeamRequest fromTeamGatewayRqToTeamRequest(TeamGatewayRq teamGatewayRq);
}
