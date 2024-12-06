package com.personal.project.controller;

import com.personal.model.dto.RoleResponse;
import com.personal.model.dto.UserResponse;
import com.personal.project.model.UserRq;
import com.personal.project.service.contract.RolesService;
import com.personal.project.service.contract.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final RolesService rolesService;
    private final UserService userService;

    @GetMapping("/roles")
    public List<String> findAllRoles() {
        return rolesService.findAllRoles();
    }

    @PostMapping("/role/{role}")
    public RoleResponse createRole(@PathVariable("role") String role) {
        return rolesService.create(role);
    }

    @PutMapping("/role/{oldRole}/{newRole}")
    public boolean renameRole(@PathVariable("oldRole") String oldRole,
                              @PathVariable("newRole") String newRole) {
        return rolesService.rename(oldRole, newRole);
    }

    @DeleteMapping("/role/{role}")
    public boolean deleteRole(@PathVariable("role") String role) {
        return rolesService.delete(role);
    }

    @GetMapping("/role/{role}/users")
    public List<UserResponse> findAllRoleUsers(@PathVariable("role") String role) {
        return userService.findAllUsersByRole(role);
    }

    @GetMapping("/roles/users-count")
    public List<RoleResponse> findAllRolesUsersCount() {
        return rolesService.findAllRolesUsersCount();
    }

    //project?page=0&size=10&sortBy=id
    @GetMapping("/project/users")
    public Page<UserResponse> getUsers(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sortBy) {
        return userService.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    //search?firstName=Aaron&page=0&size=2&sortBy=firstName
    @GetMapping("/search")
    public Page<UserResponse> searchUsers(@RequestParam String firstName,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy) {
        return userService.searchUsersByFirstName(firstName, PageRequest.of(page, size, Sort.by(sortBy)));
    }

    @GetMapping("/user/{id}")
    public UserResponse findUserById(@PathVariable("id") int id) {
        return userService.findById(id);
    }

    @GetMapping("/user/username/{username}")
    public UserResponse findUserByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @PutMapping("/user")
    public UserResponse updateUser(@Valid @RequestBody UserRq userRq) {
        return userService.update(userRq);
    }

    @PutMapping("/user/{username}/{role}")
    public UserResponse addRoleToUser(@PathVariable("username") String username,
                                      @PathVariable("role") String role) {
        return userService.addRoleToUser(username, role);
    }

    @DeleteMapping("/user/{username}")
    public boolean deleteUser(@PathVariable("username") String username) {
        return userService.delete(username);
    }
}
