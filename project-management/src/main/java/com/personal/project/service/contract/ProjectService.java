package com.personal.project.service.contract;

import com.personal.model.dto.ProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProjectService {
    Page<ProjectResponse> findAll(PageRequest pageable);

    ProjectResponse findByName(String name);

    ProjectResponse updateDescription(String name, String description);

    boolean delete(String name);
}
