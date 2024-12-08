package com.personal.gateway.service;

import com.personal.gateway.adapter.TeamAdapter;
import com.personal.gateway.service.contract.TeamService;
import com.personal.gateway.service.feign.ProjectManagementClient;
import com.personal.model.dto.TeamGatewayRp;
import com.personal.model.dto.TeamGatewayRq;
import com.personal.model.dto.TeamResponse;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class TeamServiceImpl implements TeamService {

    private final ProjectManagementClient projectManagementClient;
    private final TeamAdapter teamAdapter;

    @Override
    public List<TeamGatewayRp> findAll(int page, int size, String sortBy) {
        try {
            List<TeamResponse> teams = projectManagementClient.findAllTeams(page, size, sortBy);
            log.info("Retrieve all teams");
            return teams.stream().map(teamAdapter::fromTeamResponseToTeamGatewayRp).toList();
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Project Service is down");
        }
    }

    @Override
    public TeamGatewayRp findByName(String name) {
        try {
            TeamResponse team = projectManagementClient.findTeamByName(name);
            log.info(format("Retrieve team %s", name));
            return teamAdapter.fromTeamResponseToTeamGatewayRp(team);
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Project Service is down");
        }
    }

    @Override
    public TeamGatewayRp create(TeamGatewayRq teamGatewayRq) {
        try {
            TeamResponse teamResponse = projectManagementClient.createTeam(teamAdapter.fromTeamGatewayRqToTeamRequest(teamGatewayRq));
            log.info(format("Create team %s", teamGatewayRq.getName()));
            return teamAdapter.fromTeamResponseToTeamGatewayRp(teamResponse);
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Project Service is down");
        }
    }

    @Override
    public TeamGatewayRp addMemberToATeam(String name, int id) {
        try {
            TeamResponse teamResponse = projectManagementClient.addMemberToATeam(name, id);
            log.info(format("Add user %d into team %s", id, name));
            return teamAdapter.fromTeamResponseToTeamGatewayRp(teamResponse);
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Project Service is down");
        }
    }

    @Override
    public TeamGatewayRp removeMemberFromATeam(String name, int id) {
        try {
            TeamResponse teamResponse = projectManagementClient.removeMemberFromATeam(name, id);
            log.info(format("Remove team member %d from team %s", id, name));
            return teamAdapter.fromTeamResponseToTeamGatewayRp(teamResponse);
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Project Service is down");
        }
    }

    @Override
    public TeamGatewayRp addProject(String teamName, String projectName) {
        try {
            TeamResponse teamResponse = projectManagementClient.addTeamToProject(teamName, projectName);
            log.info(format("Add project %s to team %s", projectName, teamName));
            return teamAdapter.fromTeamResponseToTeamGatewayRp(teamResponse);
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Project Service is down");
        }
    }

    @Override
    public TeamGatewayRp removeProject(String teamName, String projectName) {
        try {
            TeamResponse teamResponse = projectManagementClient.removeTeamFromAProject(teamName, projectName);
            log.info(format("Remove project %s to team %s", projectName, teamName));
            return teamAdapter.fromTeamResponseToTeamGatewayRp(teamResponse);
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Project Service is down");
        }
    }

    @Override
    public boolean delete(String name) {
        try {
            boolean isDeleted = projectManagementClient.deleteATeam(name);
            if (isDeleted) {
                log.info(format("Team deleted: %s", name));
                return true;
            }
            log.warn(format("Can't delete team %s", name));
            return false;
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Project Service is down");
        }
    }

    @Override
    public List<TeamGatewayRp> search(String name, String projectName, int page, int size) {
        try {
            List<TeamResponse> teamResponses = projectManagementClient.searchTeams(name, projectName, page, size);
            log.info("Retrieve team");
            return teamResponses.stream().map(teamAdapter::fromTeamResponseToTeamGatewayRp).toList();
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Project Service is down");
        }
    }

    @Override
    public List<TeamGatewayRp> findAllProjectTeams(String name, int page, int size) {
        try {
            List<TeamResponse> teamResponses = projectManagementClient.findAllProjectTeams(name, page, size);
            log.warn(format("Retrieve teams for project %s", name));
            return teamResponses.stream().map(teamAdapter::fromTeamResponseToTeamGatewayRp).toList();
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Project Service is down");
        }
    }
}
