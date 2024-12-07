package com.personal.gateway.service.feign;

import com.personal.model.dto.RoleResponse;
import com.personal.model.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "User-Management", url = "http://localhost:8082")
public interface UserManagementClient {

    @GetMapping("/roles")
    List<String> findAllRoles();

    @PostMapping("/role/{role}")
    RoleResponse createRole(@PathVariable("role") String role);

    @PutMapping("/role/{oldRole}/{newRole}")
    boolean renameRole(@PathVariable("oldRole") String oldRole,
                       @PathVariable("newRole") String newRole);

    @DeleteMapping("/role/{role}")
    boolean deleteRole(@PathVariable("role") String role);

    @GetMapping("/role/{role}/users")
    List<UserResponse> findAllRoleUsers(@PathVariable("role") String role);
}


