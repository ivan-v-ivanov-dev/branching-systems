package com.personal.project.service;

import com.personal.model.dto.TeamResponse;
import com.personal.model.dto.UserResponse;
import com.personal.project.adapter.TeamAdapter;
import com.personal.project.model.Project;
import com.personal.project.model.Team;
import com.personal.project.model.User;
import com.personal.project.model.dto.TeamRq;
import com.personal.project.repository.ProjectRepository;
import com.personal.project.repository.TeamsRepository;
import com.personal.project.repository.UserRepository;
import com.personal.project.service.contract.TeamsService;
import com.personal.project.service.feign.UserClient;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.*;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class TeamsServiceImpl implements TeamsService {

    private final TeamsRepository teamsRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final UserClient userClient;
    private final TeamAdapter teamAdapter;

    @Override
    public List<TeamResponse> findAll(PageRequest pageable) {
        List<TeamResponse> response = new ArrayList<>();
        try {
            Page<Team> teams = teamsRepository.findAll(pageable);
            List<TeamResponse> temp = new ArrayList<>();
            for (Team team : teams) {
                TeamResponse teamResponse = teamAdapter.fromTeamToTeamResponse(team);
                teamResponse.setLeader(userClient.findUserById(team.getLeader().getId()));
                Set<UserResponse> members = new HashSet<>();
                team.getMembers().forEach(e -> members.add(userClient.findUserById(e.getId())));
                teamResponse.setMembers(members);
                temp.add(teamResponse);
            }
            response.addAll(temp);
            log.info("Retrieve all teams");
            return response;
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("User service is down");
        } catch (IllegalArgumentException illegalArgumentException) {
            log.error(illegalArgumentException.getMessage());
            return response;
        }
    }

    @Override
    public TeamResponse findByName(String name) {
        try {
            Team team = teamsRepository.findByName(name);
            TeamResponse response = teamAdapter.fromTeamToTeamResponse(team);
            response.setLeader(userClient.findUserById(team.getLeader().getId()));
            Set<UserResponse> members = new HashSet<>();
            team.getMembers().forEach(e -> members.add(userClient.findUserById(e.getId())));
            response.setMembers(members);
            log.info(format("Retrieve team %s", name));
            return response;
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("User service is down");
        }
    }

    @Transactional
    @Override
    public TeamResponse create(TeamRq teamRq) {
        Optional<User> leader = userRepository.findById(teamRq.getLeaderId());
        if (leader.isEmpty()) {
            log.error(format("There is no leader with id %d", teamRq.getProjectId()));
            throw new IllegalArgumentException(format("There is no leader with id %d", teamRq.getProjectId()));
        }
        Optional<Project> project = projectRepository.findById(teamRq.getProjectId());
        if (project.isEmpty()) {
            log.error(format("There is no project with id %d", teamRq.getProjectId()));
            throw new IllegalArgumentException(format("There is no project with id %d", teamRq.getProjectId()));
        }
        Team team = Team.builder()
                .name(teamRq.getName())
                .leader(leader.get()).build();
        log.info("Create %s team");
        TeamResponse response = teamAdapter.fromTeamToTeamResponse(teamsRepository.save(team));
        response.setLeader(userClient.findUserById(teamRq.getLeaderId()));
        return response;
    }

    @Transactional
    @Override
    public TeamResponse addMemberToATeam(String name, int id) {
        Team team = teamsRepository.findByName(name);
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            log.error(format("There is no user with id %d", id));
            throw new IllegalArgumentException(format("There is no user with id %d", id));
        }

        team.getMembers().add(user.get());
        log.info(format("Add user %d into team %s", id, name));
        return teamAdapter.fromTeamToTeamResponse(teamsRepository.save(team));
    }

    @Transactional
    @Override
    public TeamResponse removeMemberFromATeam(String name, int id) {
        Team team = teamsRepository.findByName(name);
        team.getMembers().removeIf(current -> current.getId() == id);
        log.info(format("Remove team member %d from team %s", id, name));
        return teamAdapter.fromTeamToTeamResponse(teamsRepository.save(team));
    }

    @Override
    public TeamResponse addProject(String teamName, String projectName) {
        Team team = teamsRepository.findByName(teamName);
        Project project = projectRepository.findByName(projectName);
        team.getProjects().add(project);
        log.info(format("Add project %s to team %s", projectName, teamName));
        return teamAdapter.fromTeamToTeamResponse(teamsRepository.save(team));
    }

    @Override
    public TeamResponse removeProject(String teamName, String projectName) {
        Team team = teamsRepository.findByName(teamName);
        Project project = projectRepository.findByName(projectName);
        team.getProjects().remove(project);
        log.info(format("Remove project %s to team %s", projectName, teamName));
        return teamAdapter.fromTeamToTeamResponse(teamsRepository.save(team));
    }

    @Transactional
    @Override
    public boolean delete(String name) {
        int isDeleted = teamsRepository.deleteByName(name);
        if (isDeleted <= 0) {
            log.warn(format("Can't delete %s", name));
            return false;
        }
        log.info(format("Team deleted: %s", name));
        return true;
    }

    @Override
    public List<TeamResponse> searchTeams(String name, String projectName, int page, int size) {
        List<Team> teams = teamsRepository.searchByNameAndProjectName(name, projectName, size, page);
        log.info(format("Retrieve %s team", name));
        return teams.stream().map(teamAdapter::fromTeamToTeamResponse).toList();
    }

    @Override
    public List<TeamResponse> findAllProjectTeams(String name, int page, int size) {
        List<Team> teams = teamsRepository.findAllProjectTeams(name, page, size);
        log.warn(format("Retrieve teams for project %s", name));
        return teams.stream().map(teamAdapter::fromTeamToTeamResponse).toList();
    }
}
