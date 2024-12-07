package com.personal.gateway.service;

import com.personal.gateway.adapter.UserAdapter;
import com.personal.gateway.service.contract.UserService;
import com.personal.gateway.service.feign.UserManagementClient;
import com.personal.model.dto.UserGatewayRp;
import com.personal.model.dto.UserResponse;
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
public class UserServiceImpl implements UserService {

    private final UserManagementClient userManagementClient;
    private final UserAdapter userAdapter;

    @Override
    public List<UserGatewayRp> findAllRoleUsers(String role) {
        try {
            List<UserResponse> userResponses = userManagementClient.findAllRoleUsers(role);
            log.info(format("Retrieve all users for role %s", role));
            return userResponses.stream().map(userAdapter::fromUserResponseToUserGatewayRp).toList();
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("User Service is down");
        }
    }
}
