package com.personal.project.service.contract;

import com.personal.project.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TeamsService {
    Page<Team> findAll(PageRequest pageable);

    Team findByName(String name);

    Team addMemberToATeam(String name, int id);

    Team removeMemberFromATeam(String name, int id);
}
