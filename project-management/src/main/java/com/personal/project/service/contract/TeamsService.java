package com.personal.project.service.contract;

import com.personal.project.model.Project;
import com.personal.project.model.Team;
import com.personal.project.model.TeamRq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TeamsService {
    Page<Team> findAll(PageRequest pageable);

    Team findByName(String name);

    Team create(TeamRq teamRq);

    Team addMemberToATeam(String name, int id);

    Team removeMemberFromATeam(String name, int id);

    boolean delete(String name);

    Page<Team> searchTeams(String name, String projectName, PageRequest pageable);

    Team addProject(String teamName, String projectName);

    Team removeProject(String teamName, String projectName);
}
