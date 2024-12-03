package com.personal.users.service.contract;

import com.personal.users.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsersByRole(String role);

    List<User> findAll();

    User findByUsername(String username);
}
