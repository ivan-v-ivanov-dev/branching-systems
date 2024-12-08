package com.personal.gateway.service.contract;

import com.personal.model.dto.RoleGatewayRp;
import com.personal.model.dto.UserResponse;

import java.util.List;

public interface RoleService {
    List<String> findAllRoles();

    RoleGatewayRp create(String role);

    boolean rename(String oldName, String newName);

    boolean delete(String role);

    List<RoleGatewayRp> findAllRolesUsersCount();
}
