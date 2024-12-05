package com.personal.user.controller;

import com.personal.model.model.UserResponse;
import com.personal.user.model.Role;
import com.personal.user.model.User;
import com.personal.user.model.UserRq;
import com.personal.user.service.contract.RolesService;
import com.personal.user.service.contract.UserService;
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
    public Role createRole(@PathVariable("role") String role) {
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

    @GetMapping("/role/{role}/project")
    public List<User> findAllRoleUsers(@PathVariable("role") String role) {
        return userService.findAllUsersByRole(role);
    }

    @GetMapping("/roles/project-count")
    public List<Role> findAllRolesUsersCount() {
        return rolesService.findAllRolesUsersCount();
    }

    //project?page=0&size=10&sortBy=id
    @GetMapping("/project")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sortBy) {
        return userService.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    //search?firstName=Aaron&page=0&size=2&sortBy=firstName
    @GetMapping("/search")
    public Page<User> searchUsers(@RequestParam String firstName,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return userService.searchUsersByFirstName(firstName, PageRequest.of(page, size, Sort.by(sortBy)));
    }

    @GetMapping("/user/{id}")
    public UserResponse findUserById(@PathVariable("id") int id) {
        return userService.findById(id);
    }

    @GetMapping("/user/{username}")
    public User findUserByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @PutMapping("/user")
    public User updateUser(@Valid @RequestBody UserRq userRq) {
        return userService.update(userRq);
    }

    @PutMapping("/user/{username}/{role}")
    public User addRoleToUser(@PathVariable("username") String username,
                              @PathVariable("role") String role) {
        return userService.addRoleToUser(username, role);
    }

    @DeleteMapping("/user/{username}")
    public boolean deleteUser(@PathVariable("username") String username) {
        return userService.delete(username);
    }
}
