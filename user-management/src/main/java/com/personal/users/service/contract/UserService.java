package com.personal.users.service.contract;

import com.personal.users.model.User;
import com.personal.users.model.UserRq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<User> findAllUsersByRole(String role);

    Page<User> findAll(Pageable pageable);

    Page<User> searchUsersByFirstName(String firstName, Pageable pageable);

    User findByUsername(String username);

    User update(UserRq userRq);

    boolean delete(String username);
}
