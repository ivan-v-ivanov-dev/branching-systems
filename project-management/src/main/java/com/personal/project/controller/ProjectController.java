package com.personal.project.controller;

import com.personal.project.model.Project;
import com.personal.project.model.Team;
import com.personal.project.model.TeamRq;
import com.personal.project.service.contract.ProjectService;
import com.personal.project.service.contract.TeamsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class ProjectController {

    private final TeamsService teamsService;
    private final ProjectService projectService;

    @GetMapping("/team/{name}")
    public Team findTeamByName(@PathVariable("name") String name) {
        return teamsService.findByName(name);
    }

    @GetMapping("/teams")
    public Page<Team> getTeams(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return teamsService.findAll(PageRequest.of(page, size));
    }

    @PostMapping("/team")
    public Team createTeam(@Valid @RequestBody TeamRq teamRq) {
        return teamsService.create(teamRq);
    }

    @PutMapping("/team/{name}/member/{id}/add")
    public Team addMemberToATeam(@PathVariable("name") String name,
                                 @PathVariable("id") int id) {
        return teamsService.addMemberToATeam(name, id);
    }

    @PutMapping("/team/{name}/member/{id}/remove")
    public Team removeMemberFromATeam(@PathVariable("name") String name,
                                      @PathVariable("id") int id) {
        return teamsService.removeMemberFromATeam(name, id);
    }

    @DeleteMapping("/team/{name}")
    public boolean deleteATeam(@PathVariable("name") String name) {
        return teamsService.delete(name);
    }

    @GetMapping("/teams/search")
    public Page<Team> searchTeams(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                  @RequestParam(value = "projectName", required = false, defaultValue = "") String projectName,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        return teamsService.searchTeams(name, projectName, PageRequest.of(page, size));
    }

    @GetMapping("/projects")
    public Page<Project> findAllProjects(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return projectService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/project/{name}")
    public Project findProjectByName(@PathVariable("name") String name) {
        return projectService.findByName(name);
    }

    @PutMapping("/project/{name}")
    public Project updateProjectDescription(@PathVariable("name") String name,
                                            @RequestParam("description") String description) {
        return projectService.updateDescription(name, description);
    }
}
