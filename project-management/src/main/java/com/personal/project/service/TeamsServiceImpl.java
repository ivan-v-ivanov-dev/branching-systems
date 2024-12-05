package com.personal.project.service;

import com.personal.project.model.Project;
import com.personal.project.model.Team;
import com.personal.project.model.TeamRq;
import com.personal.project.model.User;
import com.personal.project.repository.ProjectRepository;
import com.personal.project.repository.TeamsRepository;
import com.personal.project.repository.UserRepository;
import com.personal.project.service.contract.TeamsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class TeamsServiceImpl implements TeamsService {

    private final TeamsRepository teamsRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

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

    @Transactional
    @Override
    public Team create(TeamRq teamRq) {
        Optional<User> leader = userRepository.findById(teamRq.getLeaderId());
        if (leader.isEmpty()) {
            //TODO: Retrieve leader from the other service
            //if there is no user throw exception
        }

        // TODO Use project name
        Optional<Project> project = projectRepository.findById(teamRq.getProjectId());
        if (project.isEmpty()) {
            log.error(format("There is no project with id %d", teamRq.getProjectId()));
            throw new IllegalArgumentException(format("There is no project with id %d", teamRq.getProjectId()));
        }
        Team team = Team.builder()
                .name(teamRq.getName())
                .project(project.get())
                .leader(leader.get()).build();
        log.info("Create %s team");
        return teamsRepository.save(team);
    }

    @Transactional
    @Override
    public Team addMemberToATeam(String name, int id) {
        Team team = teamsRepository.findByName(name);
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            //TODO: Retrieve user form the other service and store it in the database
        }

        team.getMembers().add(user.get());
        log.info(format("Add user %d into team %s", id, name));
        return teamsRepository.save(team);
    }

    @Transactional
    @Override
    public Team removeMemberFromATeam(String name, int id) {
        Team team = teamsRepository.findByName(name);
        team.getMembers().removeIf(current -> current.getId() == id);
        log.info(format("Remove team member %d from team %s", id, name));
        return teamsRepository.save(team);
    }

    @Override
    public boolean delete(String name) {
        int isDeleted = teamsRepository.deleteByName(name);
        if (isDeleted <= 0) {
            log.warn(format("Can't delete %s", name));
            return false;
        }
        log.info(format("Team deleted: %s", name));
        return true;
    }

    @Override
    public Page<Team> searchTeams(String name, String projectName, PageRequest pageable) {
        Page<Team> team = teamsRepository.searchByNameAndProjectName(name, projectName, pageable);
        log.info(format("Retrieve %s team", name));
        return team;
    }
}
