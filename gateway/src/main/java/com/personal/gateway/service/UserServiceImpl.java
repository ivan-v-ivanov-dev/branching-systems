package com.personal.gateway.service;

import com.personal.gateway.adapter.UserAdapter;
import com.personal.gateway.service.contract.UserService;
import com.personal.gateway.service.feign.UserManagementClient;
import com.personal.model.dto.UserGatewayRp;
import com.personal.model.dto.UserGatewayRq;
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

    @Override
    public List<UserGatewayRp> searchUsers(String firstName, int page, int size, String sortBy) {
        try {
            List<UserResponse> userResponses = userManagementClient.searchUsers(firstName, page, size, sortBy);
            log.info(format("Search users by first name %s", firstName));
            return userResponses.stream().map(userAdapter::fromUserResponseToUserGatewayRp).toList();
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("User Service is down");
        }
    }

    @Override
    public List<UserGatewayRp> findAll(int page, int size, String sortBy) {
        try {
            List<UserResponse> users = userManagementClient.findAllUsers(page, size, sortBy);
            log.info("Retrieve all users");
            return users.stream().map(userAdapter::fromUserResponseToUserGatewayRp).toList();
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("User Service is down");
        }
    }

    @Override
    public UserGatewayRp findUserByUsername(String username) {
        try {
            UserResponse userResponse = userManagementClient.findUserByUsername(username);
            log.info(format("Retrieve user %s", username));
            return userAdapter.fromUserResponseToUserGatewayRp(userResponse);
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("User Service is down");
        }
    }

    @Override
    public UserGatewayRp update(UserGatewayRq userGatewayRq) {
        try {
            UserResponse userResponse = userManagementClient.updateUser(userAdapter.fromUserGatewayRqToUserRequest(userGatewayRq));
            log.info(format("Update user %s", userResponse.getUsername()));
            return userAdapter.fromUserResponseToUserGatewayRp(userResponse);
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("User Service is down");
        }
    }

    @Override
    public UserGatewayRp addRoleToUser(String username, String role) {
        try {
            UserResponse userResponse = userManagementClient.addRoleToUser(username, role);
            log.info(format("User %s updated with role %s", username, role));
            return userAdapter.fromUserResponseToUserGatewayRp(userResponse);
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("User Service is down");
        }
    }
}
