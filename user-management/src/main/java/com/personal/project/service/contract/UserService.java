package com.personal.project.service.contract;

import com.personal.model.dto.UserResponse;
import com.personal.project.model.UserRq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<UserResponse> findAllUsersByRole(String role);

    Page<UserResponse> searchUsersByFirstName(String firstName, Pageable pageable);

    UserResponse findById(int id);

    UserResponse findByUsername(String username);

    UserResponse update(UserRq userRq);

    UserResponse addRoleToUser(String username, String role);

    boolean delete(String username);

}
