package com.personal.project.controller;

import com.personal.project.model.Team;
import com.personal.project.service.contract.TeamsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ProjectController {

    private final TeamsService teamsService;

    @GetMapping("/team/{name}")
    public Team findTeamByName(@PathVariable("name") String name) {
        return teamsService.findByName(name);
    }

    @PutMapping("/team/{name}/member/{id}/add")
    public Team addMemberToATeam(@PathVariable("name") String name,
                                 @PathVariable("id") int id) {
        return teamsService.addMemberToATeam(name, id);
    }

    @GetMapping("/teams")
    public Page<Team> getTeams(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return teamsService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/health")
    public String health() {
        return "Healthy";
    }
}
