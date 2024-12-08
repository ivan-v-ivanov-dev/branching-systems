package com.personal.gateway.controller;

import com.personal.gateway.service.contract.RoleService;
import com.personal.gateway.service.contract.UserService;
import com.personal.model.dto.RoleGatewayRp;
import com.personal.model.dto.UserGatewayRp;
import com.personal.model.dto.UserGatewayRq;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiGatewayController {

    private final RoleService roleService;
    private final UserService userService;

    //TODO: Restrict for CEO
    @GetMapping("/roles")
    public List<String> findAllRoles() {
        return roleService.findAllRoles();
    }

    //TODO: Restrict for CEO
    @PostMapping("/role/{role}")
    public RoleGatewayRp createRole(@PathVariable("role") String role) {
        return roleService.create(role);
    }

    //TODO: Restrict for CEO
    @PutMapping("/role/{oldName}/{newName}")
    public boolean renameRole(@PathVariable("oldName") String oldName,
                              @PathVariable("newName") String newName) {
        return roleService.rename(oldName, newName);
    }

    //TODO: Restrict for CEO
    @DeleteMapping("/role/{role}")
    public boolean deleteRole(@PathVariable("role") String role) {
        return roleService.delete(role);
    }

    //TODO: Restrict for CEO
    @GetMapping("/role/{role}/users")
    public List<UserGatewayRp> findAllRoleUsers(@PathVariable("role") String role) {
        return userService.findAllRoleUsers(role);
    }

    //TODO: Restrict for CEO
    @GetMapping("/roles/users-count")
    public List<RoleGatewayRp> findAllRolesUsersCount() {
        return roleService.findAllRolesUsersCount();
    }

    //TODO: Restrict for CEO
    @GetMapping("/users/search")
    public List<UserGatewayRp> searchUsers(@RequestParam String firstName,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        return userService.searchUsers(firstName, page, size, sortBy);
    }

    //TODO: Restrict for CEO
    @GetMapping("/user/{username}")
    public UserGatewayRp findUserByUsername(@PathVariable("username") String username) {
        return userService.findUserByUsername(username);
    }

    @PutMapping("/user")
    public UserGatewayRp updateUser(@Valid @RequestBody UserGatewayRq userGatewayRq) {
        return userService.update(userGatewayRq);
    }
}
