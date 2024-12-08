package com.personal.project.service;

import com.personal.model.dto.ProjectResponse;
import com.personal.project.adapter.TeamAdapter;
import com.personal.project.model.Project;
import com.personal.project.repository.ProjectRepository;
import com.personal.project.service.contract.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamAdapter teamAdapter;

    @Override
    public List<ProjectResponse> findAll(PageRequest pageable) {
        Page<Project> projects = projectRepository.findAll(pageable);
        log.info("Retrieve projects");
        return projects.stream().map(teamAdapter::projectToProjectResponse).toList();
    }

    @Override
    public ProjectResponse findByName(String name) {
        Project project = projectRepository.findByName(name);
        log.info(format("Retrieve project %s", name));
        return teamAdapter.projectToProjectResponse(project);
    }

    @Transactional
    @Override
    public ProjectResponse updateDescription(String name, String description) {
        Project project = projectRepository.findByName(name);
        if (project == null) {
            log.error(format("No such project %s", name));
            throw new IllegalArgumentException(format("No such project %s", name));
        }

        project = project.toBuilder().description(description).build();
        log.info(format("Update project description %s", name));
        return teamAdapter.projectToProjectResponse(projectRepository.save(project));
    }

    @Transactional
    @Override
    public boolean delete(String name) {
        int isDeleted = projectRepository.deleteByName(name);
        if (isDeleted <= 0) {
            log.warn(format("Can't delete %s", name));
            return false;
        }
        log.info(format("Project deleted: %s", name));
        return true;
    }
}
