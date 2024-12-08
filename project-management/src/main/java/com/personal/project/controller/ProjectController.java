package com.personal.project.controller;

import com.personal.model.dto.ProjectResponse;
import com.personal.model.dto.TeamRequest;
import com.personal.model.dto.TeamResponse;
import com.personal.project.service.contract.ProjectService;
import com.personal.project.service.contract.TeamsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProjectController {

    private final TeamsService teamsService;
    private final ProjectService projectService;

    @GetMapping("/team/{name}")
    public TeamResponse findTeamByName(@PathVariable("name") String name) {
        return teamsService.findByName(name);
    }

    @GetMapping("/teams")
    public List<TeamResponse> findAllTeams(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        return teamsService.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    @PostMapping("/team")
    public TeamResponse createTeam(@RequestBody TeamRequest teamRequest) {
        return teamsService.create(teamRequest);
    }

    @PutMapping("/team/{name}/member/{id}/add")
    public TeamResponse addMemberToATeam(@PathVariable("name") String name,
                                         @PathVariable("id") int id) {
        return teamsService.addMemberToATeam(name, id);
    }

    @PutMapping("/team/{name}/member/{id}/remove")
    public TeamResponse removeMemberFromATeam(@PathVariable("name") String name,
                                              @PathVariable("id") int id) {
        return teamsService.removeMemberFromATeam(name, id);
    }

    @PutMapping("/team/{teamName}/add-project/{projectName}")
    public TeamResponse addTeamToProject(@PathVariable("teamName") String teamName,
                                         @PathVariable("projectName") String projectName) {
        return teamsService.addProject(teamName, projectName);
    }

    @PutMapping("/team/{teamName}/remove-project/{projectName}")
    public TeamResponse removeTeamFromAProject(@PathVariable("teamName") String teamName,
                                               @PathVariable("projectName") String projectName) {
        return teamsService.removeProject(teamName, projectName);
    }

    @DeleteMapping("/team/{name}")
    public boolean deleteATeam(@PathVariable("name") String name) {
        return teamsService.delete(name);
    }

    @GetMapping("/teams/search")
    public List<TeamResponse> searchTeams(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                          @RequestParam(value = "projectName", required = false, defaultValue = "") String projectName,
                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size) {
        return teamsService.searchTeams(name, projectName, page, size);
    }

    @GetMapping("/projects")
    public List<ProjectResponse> findAllProjects(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return projectService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/project/{name}")
    public ProjectResponse findProjectByName(@PathVariable("name") String name) {
        return projectService.findByName(name);
    }

    @PutMapping("/project/{name}")
    public ProjectResponse updateProjectDescription(@PathVariable("name") String name,
                                                    @RequestParam("description") String description) {
        return projectService.updateDescription(name, description);
    }

    @DeleteMapping("/project/{name}")
    public boolean deleteProject(@PathVariable("name") String name) {
        return projectService.delete(name);
    }

    //project?page=0&size=10&sortBy=id
    @GetMapping("/project/{name}/teams")
    public List<TeamResponse> findAllProjectTeams(@PathVariable("name") String name,
                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        return teamsService.findAllProjectTeams(name, page, size);
    }
}
