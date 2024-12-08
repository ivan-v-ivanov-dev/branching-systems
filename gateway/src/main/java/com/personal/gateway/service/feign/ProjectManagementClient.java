package com.personal.gateway.service.feign;

import com.personal.model.dto.TeamRequest;
import com.personal.model.dto.TeamResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Project-Management", url = "http://localhost:8083")
public interface
ProjectManagementClient {

    @GetMapping("/teams")
    List<TeamResponse> findAllTeams(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy);

    @GetMapping("/team/{name}")
    TeamResponse findTeamByName(@PathVariable("name") String name);

    @PostMapping("/team")
    TeamResponse createTeam(@RequestBody TeamRequest teamRequest);

    @PutMapping("/team/{name}/member/{id}/add")
    TeamResponse addMemberToATeam(@PathVariable("name") String name,
                                  @PathVariable("id") int id);

    @PutMapping("/team/{name}/member/{id}/remove")
    TeamResponse removeMemberFromATeam(@PathVariable("name") String name,
                                       @PathVariable("id") int id);

    @PutMapping("/team/{teamName}/add-project/{projectName}")
    TeamResponse addTeamToProject(@PathVariable("teamName") String teamName,
                                         @PathVariable("projectName") String projectName);
}
