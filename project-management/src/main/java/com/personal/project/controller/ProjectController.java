package com.personal.project.controller;

import com.personal.project.model.Team;
import com.personal.project.service.contract.TeamsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProjectController {

    private final TeamsService teamsService;

    @GetMapping("/team/{name}")
    public Team findTeamByName(@PathVariable("name") String name) {
        return teamsService.findByName(name);
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
