package com.personal.gateway.service;

import com.personal.gateway.adapter.RoleAdapter;
import com.personal.gateway.service.contract.RoleService;
import com.personal.gateway.service.feign.UserManagementClient;
import com.personal.model.dto.RoleGatewayRp;
import com.personal.model.dto.RoleResponse;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final UserManagementClient userManagementClient;
    private final RoleAdapter roleAdapter;

    @Override
    public List<String> findAllRoles() {
        try {
            List<String> roles = userManagementClient.findAllRoles();
            log.info("Retrieve all distinct roles");
            return roles;
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException(feignException.getMessage());
        }
    }

    @Override
    public RoleGatewayRp create(String role) {
        try {
            RoleResponse response = userManagementClient.createRole(role);
            log.info(format("Create role: %s", role));
            return roleAdapter.fromRoleResponseToRoleGatewayResponse(response);
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("User Service is down");
        }
    }

    @Override
    public boolean rename(String oldName, String newName) {
        try {
            boolean isRenamed = userManagementClient.renameRole(oldName, newName);
            log.info(isRenamed ? format("Role renamed %s", newName) : format("Can't rename role %s", oldName));
            return isRenamed;
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("User Service is down");
        }
    }

    @Override
    public boolean delete(String role) {
        try {
            boolean isDeleted = userManagementClient.deleteRole(role);
            log.info(isDeleted ? format("Role %s deleted", role) : "Role %s not deleted");
            return isDeleted;
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("User Service is down");
        }
    }
}

