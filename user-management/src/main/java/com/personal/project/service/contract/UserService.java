package com.personal.project.service.contract;

import com.personal.model.dto.UserResponse;
import com.personal.project.model.User;
import com.personal.project.model.UserRq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<User> findAllUsersByRole(String role);

    Page<User> findAll(Pageable pageable);

    Page<User> searchUsersByFirstName(String firstName, Pageable pageable);

    UserResponse findById(int id);

    User findByUsername(String username);

    User update(UserRq userRq);

    User addRoleToUser(String username, String role);

    boolean delete(String username);

}
