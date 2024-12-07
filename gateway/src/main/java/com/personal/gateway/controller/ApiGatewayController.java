package com.personal.gateway.controller;

import com.personal.gateway.service.contract.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.DocFlavor;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiGatewayController {

    private final RoleService roleService;

    @GetMapping("/roles")
    public List<String> findAllRoles() {
        return roleService.findAllRoles();
    }
}
