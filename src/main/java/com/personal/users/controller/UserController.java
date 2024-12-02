package com.personal.users.controller;

import com.personal.users.model.Role;
import com.personal.users.service.contract.RolesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final RolesService rolesService;

    @GetMapping("/roles")
    public List<String> findAllRoles() {
        return rolesService.findAllRoles();
    }

    @PostMapping("/role")
    public Role createRole(@RequestParam("role") String role) {
        return rolesService.create(role);
    }

    @DeleteMapping("/role")
    public boolean deleteRole(@RequestParam("role") String role) {
        return rolesService.delete(role);
    }

    @GetMapping
    public String health() {
        return "Healthy";
    }
}
