package com.personal.users.controller;

import com.personal.users.model.Role;
import com.personal.users.model.User;
import com.personal.users.service.contract.RolesService;
import com.personal.users.service.contract.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public String health() {
        return "Healthy";
    }
}
