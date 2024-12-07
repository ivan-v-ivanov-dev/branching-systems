package com.personal.project.service.contract;

import com.personal.model.dto.TeamResponse;
import com.personal.project.model.Team;
import com.personal.project.model.dto.TeamRq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface TeamsService {
    Page<TeamResponse> findAll(PageRequest pageable);

    TeamResponse findByName(String name);

    TeamResponse create(TeamRq teamRq);

    TeamResponse addMemberToATeam(String name, int id);

    TeamResponse removeMemberFromATeam(String name, int id);

    boolean delete(String name);

    List<TeamResponse> searchTeams(String name, String projectName, int page, int size);

    TeamResponse addProject(String teamName, String projectName);

    TeamResponse removeProject(String teamName, String projectName);

    List<TeamResponse> findAllProjectTeams(String name, int page, int size);
}
