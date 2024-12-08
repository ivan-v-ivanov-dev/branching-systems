package com.personal.gateway.service;

import com.personal.gateway.adapter.ProjectAdapter;
import com.personal.gateway.service.contract.ProjectService;
import com.personal.gateway.service.feign.ProjectManagementClient;
import com.personal.model.dto.ProjectGatewayRp;
import com.personal.model.dto.ProjectResponse;
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
public class ProjectServiceImpl implements ProjectService {

    private final ProjectManagementClient projectManagementClient;
    private final ProjectAdapter projectAdapter;

    @Override
    public List<ProjectGatewayRp> findAll(int page, int size) {
        try {
            List<ProjectResponse> projects = projectManagementClient.findAllProjects(page, size);
            log.info("Retrieve all projects");
            return projects.stream().map(projectAdapter::fromProjectResponseToProjectGatewayRp).toList();
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Project Service is down");
        }
    }

    @Override
    public ProjectGatewayRp findByName(String name) {
        try {
            ProjectResponse projectResponse = projectManagementClient.findProjectByName(name);
            log.info(format("Retrieve project %s", projectResponse.getName()));
            return projectAdapter.fromProjectResponseToProjectGatewayRp(projectResponse);
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Project Service is down");
        }
    }

    @Override
    public ProjectGatewayRp update(String name, String description) {
        try {
            ProjectResponse projectResponse = projectManagementClient.updateProjectDescription(name, description);
            log.info(format("Update project description %s", name));
            return projectAdapter.fromProjectResponseToProjectGatewayRp(projectResponse);
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Project Service is down");
        }
    }

    @Override
    public boolean delete(String name) {
        try {
            boolean isDeleted = projectManagementClient.deleteATeam(name);
            log.info(format("Delete project %s", name));
            return isDeleted;
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Project Service is down");
        }
    }
}
