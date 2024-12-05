package com.personal.project.service.contract;

import com.personal.project.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProjectService {
    Page<Project> findAll(PageRequest pageable);
}
