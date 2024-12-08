package com.personal.gateway.service;

import com.personal.gateway.adapter.TeamAdapter;
import com.personal.gateway.service.contract.TeamService;
import com.personal.gateway.service.feign.ProjectManagementClient;
import com.personal.model.dto.TeamGatewayRp;
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
}
