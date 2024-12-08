package com.personal.project.service.contract;

import com.personal.model.dto.ProjectResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProjectService {
    List<ProjectResponse> findAll(PageRequest pageable);

    ProjectResponse findByName(String name);

    ProjectResponse updateDescription(String name, String description);

    boolean delete(String name);
}
