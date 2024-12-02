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

    @PostMapping("/role/{role}")
    public Role createRole(@PathVariable("role") String role) {
        return rolesService.create(role);
    }

    @DeleteMapping("/role/{role}")
    public boolean deleteRole(@PathVariable("role") String role) {
        return rolesService.delete(role);
    }

    @GetMapping
    public String health() {
        return "Healthy";
    }
}
