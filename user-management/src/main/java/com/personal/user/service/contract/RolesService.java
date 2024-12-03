package com.personal.user.service.contract;

import com.personal.user.model.Role;

import java.util.List;

public interface RolesService {
    List<String> findAllRoles();

    Role create(String role);

    boolean rename(String oldRole, String newRole);

    boolean delete(String role);

    List<Role> findAllRolesUsersCount();
}
