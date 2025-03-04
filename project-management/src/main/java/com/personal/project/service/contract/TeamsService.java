package com.personal.project.service.contract;

import com.personal.model.dto.TeamRequest;
import com.personal.model.dto.TeamResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface TeamsService {
    List<TeamResponse> findAll(PageRequest pageable);

    TeamResponse findByName(String name);

    TeamResponse create(TeamRequest teamRequest);

    TeamResponse addMemberToATeam(String name, int id);

    TeamResponse removeMemberFromATeam(String name, int id);

    boolean delete(String name);

    List<TeamResponse> searchTeams(String name, String projectName, int page, int size);

    TeamResponse addProject(String teamName, String projectName);

    TeamResponse removeProject(String teamName, String projectName);

    List<TeamResponse> findAllProjectTeams(String name, int page, int size);
}
