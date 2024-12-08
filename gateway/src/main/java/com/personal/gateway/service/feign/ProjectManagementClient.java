package com.personal.gateway.service.feign;

import com.personal.model.dto.TeamResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "Project-Management", url = "http://localhost:8083")
public interface ProjectManagementClient {

    @GetMapping("/teams")
    List<TeamResponse> findAllTeams(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy);

    @GetMapping("/team/{name}")
    TeamResponse findTeamByName(@PathVariable("name") String name);
}
