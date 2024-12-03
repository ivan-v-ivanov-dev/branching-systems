package com.personal.project.service;

import com.personal.project.model.Team;
import com.personal.project.repository.query.TeamsRepository;
import com.personal.project.service.contract.TeamsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class TeamsServiceImpl implements TeamsService {

    private final TeamsRepository teamsRepository;

    @Override
    public Page<Team> findAll(PageRequest pageable) {
        Page<Team> teams = teamsRepository.findAll(pageable);
        log.info("Retrieve all teams");
        return teams;
    }
}
