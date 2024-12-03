package com.personal.project.service.contract;

import com.personal.project.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TeamsService {
    Page<Team> findAll(PageRequest pageable);
}
