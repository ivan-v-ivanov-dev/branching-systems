package com.personal.gateway.service;

import com.personal.gateway.service.contract.RoleService;
import com.personal.gateway.service.feign.UserManagementClient;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final UserManagementClient userManagementClient;

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
}

