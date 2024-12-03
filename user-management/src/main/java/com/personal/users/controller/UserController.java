package com.personal.users.controller;

import com.personal.users.model.Role;
import com.personal.users.model.User;
import com.personal.users.model.UserRq;
import com.personal.users.service.contract.RolesService;
import com.personal.users.service.contract.UserService;
import lombok.AllArgsConstructor;
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

    @GetMapping("/role/{role}/users")
    public List<User> findAllRoleUsers(@PathVariable("role") String role) {
        return userService.findAllUsersByRole(role);
    }

    @GetMapping("/roles/users-count")
    public List<Role> findAllRolesUsersCount() {
        return rolesService.findAllRolesUsersCount();
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/user/{username}")
    public User findUserByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @PutMapping("/user")
    public User uppdateUser(@Valid @RequestBody UserRq userRq) {
        return userService.update(userRq);
    }

    @DeleteMapping("/user/{username}")
    public boolean deleteUser(@PathVariable("username") String username) {
        return userService.delete(username);
    }

    @GetMapping
    public String health() {
        return "Healthy";
    }
}
