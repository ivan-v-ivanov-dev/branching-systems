package com.personal.gateway.controller;

import com.personal.gateway.service.contract.*;
import com.personal.model.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiGatewayController {

    private final RoleService roleService;
    private final UserService userService;
    private final TeamService teamService;
    private final ProjectService projectService;
    private final VacationService vacationService;

    @GetMapping("/roles")
    public List<String> findAllRoles() {
        return roleService.findAllRoles();
    }

    @PostMapping("/role/{role}")
    public RoleGatewayRp createRole(@PathVariable("role") String role) {
        return roleService.create(role);
    }

    @PutMapping("/role/{oldName}/{newName}")
    public boolean renameRole(@PathVariable("oldName") String oldName,
                              @PathVariable("newName") String newName) {
        return roleService.rename(oldName, newName);
    }

    @DeleteMapping("/role/{role}")
    public boolean deleteRole(@PathVariable("role") String role) {
        return roleService.delete(role);
    }

    @GetMapping("/role/{role}/users")
    public List<UserGatewayRp> findAllRoleUsers(@PathVariable("role") String role) {
        return userService.findAllRoleUsers(role);
    }

    @GetMapping("/roles/users-count")
    public List<RoleGatewayRp> findAllRolesUsersCount() {
        return roleService.findAllRolesUsersCount();
    }

    @GetMapping("/users/search")
    public List<UserGatewayRp> searchUsers(@RequestParam String firstName,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        return userService.searchUsers(firstName, page, size, sortBy);
    }

    @GetMapping("/users")
    public List<UserGatewayRp> findAllUsers(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "id") String sortBy) {
        return userService.findAll(page, size, sortBy);
    }

    @GetMapping("/user/{username}")
    public UserGatewayRp findUserByUsername(@PathVariable("username") String username) {
        return userService.findUserByUsername(username);
    }

    @PutMapping("/user")
    public UserGatewayRp updateUser(@Valid @RequestBody UserGatewayRq userGatewayRq) {
        return userService.update(userGatewayRq);
    }

    @PatchMapping("/user/{username}/{role}")
    public UserGatewayRp addRoleToUser(@PathVariable("username") String username,
                                       @PathVariable("role") String role) {
        return userService.addRoleToUser(username, role);
    }

    @DeleteMapping("/user/{username}/delete")
    public boolean deleteUser(@PathVariable("username") String username) {
        return userService.delete(username);
    }

    @GetMapping("/teams")
    List<TeamGatewayRp> getTeams(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortBy) {
        return teamService.findAll(page, size, sortBy);
    }

    @GetMapping("/team/{name}")
    public TeamGatewayRp findTeamByName(@PathVariable("name") String name) {
        return teamService.findByName(name);
    }

    @PostMapping("/team")
    public TeamGatewayRp createTeam(@Valid @RequestBody TeamGatewayRq teamGatewayRq) {
        return teamService.create(teamGatewayRq);
    }

    @PatchMapping("/team/{name}/member/{id}/add")
    public TeamGatewayRp addMemberToATeam(@PathVariable("name") String name,
                                          @PathVariable("id") int id) {
        return teamService.addMemberToATeam(name, id);
    }

    @PatchMapping("/team/{name}/member/{id}/remove")
    public TeamGatewayRp removeMemberFromATeam(@PathVariable("name") String name,
                                               @PathVariable("id") int id) {
        return teamService.removeMemberFromATeam(name, id);
    }

    @PatchMapping("/team/{teamName}/add-project/{projectName}")
    public TeamGatewayRp addTeamToProject(@PathVariable("teamName") String teamName,
                                          @PathVariable("projectName") String projectName) {
        return teamService.addProject(teamName, projectName);
    }

    @PatchMapping("/team/{teamName}/remove-project/{projectName}")
    public TeamGatewayRp removeTeamToProject(@PathVariable("teamName") String teamName,
                                             @PathVariable("projectName") String projectName) {
        return teamService.removeProject(teamName, projectName);
    }

    @DeleteMapping("/team/{name}")
    public boolean deleteATeam(@PathVariable("name") String name) {
        return teamService.delete(name);
    }

    @GetMapping("/teams/search")
    public List<TeamGatewayRp> searchTeams(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                           @RequestParam(value = "projectName", required = false, defaultValue = "") String projectName,
                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        return teamService.search(name, projectName, page, size);
    }

    @GetMapping("/projects")
    public List<ProjectGatewayRp> findAllProjects(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        return projectService.findAll(page, size);
    }

    @GetMapping("/project/{name}")
    public ProjectGatewayRp findProjectByName(@PathVariable("name") String name) {
        return projectService.findByName(name);
    }

    @PatchMapping("/project/{name}")
    public ProjectGatewayRp updateProjectDescription(@PathVariable("name") String name,
                                                     @RequestParam("description") String description) {
        return projectService.update(name, description);
    }

    @DeleteMapping("/project/{name}")
    public boolean deleteProject(@PathVariable("name") String name) {
        return projectService.delete(name);
    }

    @GetMapping("/project/{name}/teams")
    public List<TeamGatewayRp> findAllProjectTeams(@PathVariable("name") String name,
                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return teamService.findAllProjectTeams(name, page, size);
    }

    @GetMapping("/user/{name}/vacations")
    public List<VacationGatewayRp> findUserVacations(@PathVariable("name") String name) {
        return vacationService.findUserVacations(name);
    }

    @PostMapping("/vacation")
    public void createVacation(@RequestBody VacationGatewayRq vacationGatewayRq) {
        vacationService.create(vacationGatewayRq);
    }

    @PatchMapping("/vacation/{id}/sick-list")
    public void updateSickList(@PathVariable("id") String id,
                               @RequestParam MultipartFile file) {
        vacationService.updateSickList(id, file);
    }

    @PatchMapping("/vacation/{id}")
    public VacationGatewayRp updateVacation(@PathVariable("id") String id,
                                            @RequestParam(value = "startDate", required = false) String startDate,
                                            @RequestParam(value = "endDate", required = false) String endDate,
                                            @RequestParam(value = "halfDay") boolean halfDay) {
        return vacationService.update(id, startDate, endDate, halfDay);
    }

    @DeleteMapping("/vacation/{id}")
    public boolean deleteVacation(@PathVariable("id") String id) {
        return vacationService.delete(id);
    }

    @PatchMapping("/vacation/{id}/approve")
    public VacationGatewayRp approveVacation(@PathVariable("id") String id) {
        return vacationService.approve(id);
    }
}
