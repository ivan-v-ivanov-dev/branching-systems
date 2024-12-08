package com.personal.gateway.service.contract;

import com.personal.model.dto.UserGatewayRp;
import com.personal.model.dto.UserGatewayRq;
import com.personal.model.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserGatewayRp> findAllRoleUsers(String role);

    List<UserGatewayRp> searchUsers(String firstName, int page, int size, String sortBy);

    UserGatewayRp findUserByUsername(String username);

    UserGatewayRp update(UserGatewayRq userGatewayRq);
}
