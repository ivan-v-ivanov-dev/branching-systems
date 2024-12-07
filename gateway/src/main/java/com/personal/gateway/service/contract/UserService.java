package com.personal.gateway.service.contract;

import com.personal.model.dto.UserGatewayRp;

import java.util.List;

public interface UserService {
    List<UserGatewayRp> findAllRoleUsers(String role);
}
