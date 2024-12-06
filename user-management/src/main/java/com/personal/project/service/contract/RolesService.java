package com.personal.project.service.contract;

import com.personal.model.dto.RoleResponse;

import java.util.List;

public interface RolesService {
    List<String> findAllRoles();

    RoleResponse create(String role);

    boolean rename(String oldRole, String newRole);

    boolean delete(String role);

    List<RoleResponse> findAllRolesUsersCount();
}
