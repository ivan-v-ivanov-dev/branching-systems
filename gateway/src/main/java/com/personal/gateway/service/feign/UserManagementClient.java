package com.personal.gateway.service.feign;

import com.personal.model.dto.RoleResponse;
import com.personal.model.dto.UserRequest;
import com.personal.model.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/roles/users-count")
    List<RoleResponse> findAllRolesUsersCount();

    @GetMapping("/search")
    List<UserResponse> searchUsers(@RequestParam String firstName,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sortBy);

    @GetMapping("/user/username/{username}")
    UserResponse findUserByUsername(@PathVariable("username") String username);

    @PutMapping("/user")
    UserResponse updateUser(@Valid @RequestBody UserRequest userRequest);

    @PutMapping("/user/{username}/{role}")
    UserResponse addRoleToUser(@PathVariable("username") String username,
                               @PathVariable("role") String role);
}


