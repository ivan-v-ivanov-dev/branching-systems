package com.personal.project.adapter;

import com.personal.model.dto.TeamResponse;
import com.personal.project.model.Team;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamAdapter {
    TeamResponse fromTeamToTeamResponse(Team team);
}
