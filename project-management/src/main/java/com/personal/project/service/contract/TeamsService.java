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

    Team create(TeamRq teamRq);

    Team addMemberToATeam(String name, int id);

    Team removeMemberFromATeam(String name, int id);

    boolean delete(String name);

    List<Team> searchTeams(String name, String projectName, int page, int size);

    Team addProject(String teamName, String projectName);

    Team removeProject(String teamName, String projectName);
}
