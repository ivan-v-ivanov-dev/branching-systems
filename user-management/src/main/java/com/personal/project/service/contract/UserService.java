package com.personal.project.service.contract;

import com.personal.model.dto.UserRequest;
import com.personal.model.dto.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<UserResponse> findAllUsersByRole(String role);

    List<UserResponse> searchUsersByFirstName(String firstName, Pageable pageable);

    UserResponse findById(int id);

    UserResponse findByUsername(String username);

    UserResponse update(UserRequest userRequest);

    UserResponse addRoleToUser(String username, String role);

    boolean delete(String username);

}
