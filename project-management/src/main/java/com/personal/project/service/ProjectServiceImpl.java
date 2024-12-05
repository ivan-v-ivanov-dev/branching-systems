package com.personal.project.service;

import com.personal.project.model.Project;
import com.personal.project.repository.ProjectRepository;
import com.personal.project.service.contract.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Page<Project> findAll(PageRequest pageable) {
        Page<Project> projects = projectRepository.findAll(pageable);
        log.info("Retrieve projects");
        return projects;
    }

    @Override
    public Project findByName(String name) {
        Project project = projectRepository.findByName(name);
        log.info(format("Retrieve project %s", name));
        return project;
    }
}
