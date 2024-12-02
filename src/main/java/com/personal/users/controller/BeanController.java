package com.personal.users.controller;

import com.personal.users.service.contract.RolesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BeanController {

    private final RolesService rolesService;

    @GetMapping("/roles")
    public List<String> findAllRoles() {
        return rolesService.findAllRoles();
    }

    @GetMapping
    public String health() {
        return "Healthy";
    }
}
