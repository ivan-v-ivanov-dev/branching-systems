package com.personal.project.service;

import com.personal.project.model.Team;
import com.personal.project.model.User;
import com.personal.project.repository.query.TeamsRepository;
import com.personal.project.repository.query.UserRepository;
import com.personal.project.service.contract.TeamsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class TeamsServiceImpl implements TeamsService {

    private final TeamsRepository teamsRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Team> findAll(PageRequest pageable) {
        Page<Team> teams = teamsRepository.findAll(pageable);
        log.info("Retrieve all teams");
        return teams;
    }

    //TODO: Retrieve users from the other service
    @Override
    public Team findByName(String name) {
        Team team = teamsRepository.findByName(name);
        log.info(format("retrieve team %s", name));
        return team;
    }

    @Override
    public Team addMemberToATeam(String name, int id) {
        Team team = teamsRepository.findByName(name);
        Optional<User> user =  userRepository.findById(id);

        if (user.isEmpty()) {
            //TODO: Retrieve user form the other service and store it in the database
        }

        team.getMembers().add(user.get());
        log.info(format("Add user %d into team %s", id, name));
        return teamsRepository.save(team);
    }
}
