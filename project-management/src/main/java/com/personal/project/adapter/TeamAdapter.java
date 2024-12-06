package com.personal.project.adapter;

import com.personal.model.dto.ProjectResponse;
import com.personal.model.dto.TeamResponse;
import com.personal.project.model.Project;
import com.personal.project.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamAdapter {

    @Mapping(target = "name", expression = "java(team.getName())")
    @Mapping(target = "projects", expression = "java(team.getProjects().stream().map(project -> projectToProjectResponse(project)).collect(java.util.stream.Collectors.toSet()))")
    TeamResponse fromTeamToTeamResponse(Team team);

    @Mapping(target = "name", expression = "java(project.getName())")
    @Mapping(target = "description",expression = "java(project.getDescription())")
    ProjectResponse projectToProjectResponse(Project project);
}
