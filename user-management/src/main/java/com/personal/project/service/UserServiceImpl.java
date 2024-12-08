package com.personal.project.service;

import com.personal.model.dto.UserRequest;
import com.personal.model.dto.UserResponse;
import com.personal.project.adapter.UserAdapter;
import com.personal.project.model.Role;
import com.personal.project.model.User;
import com.personal.project.repository.RoleRepository;
import com.personal.project.repository.UserRepository;
import com.personal.project.service.contract.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserAdapter userAdapter;

    @Override
    public List<UserResponse> findAllUsersByRole(String role) {
        List<User> users = userRepository.findAllUsersByRole(role);
        log.info(format("Retrieve all users for role %s", role));
        return users.stream().map(userAdapter::fromUserToUserResponse).toList();
    }

    @Override
    public List<UserResponse> searchUsersByFirstName(String firstName, Pageable pageable) {
        Page<User> users = userRepository.findByFirstNameContaining(firstName, pageable);
        log.info(format("Search users by first name %s", firstName));
        return users.getContent().stream().map(userAdapter::fromUserToUserResponse).toList();
    }

    @Override
    public List<UserResponse> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        log.info("Retrieve all users");
        return users.getContent().stream().map(userAdapter::fromUserToUserResponse).toList();
    }

    @Override
    public UserResponse findById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            log.warn(format("No such user with id %d", id));
            return null;
        }
        log.info(format("Retrieve user %d", id));
        return userAdapter.fromUserToUserResponse(user.get());
    }

    @Override
    public UserResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        log.info(format("Retrieve user %s", username));
        return userAdapter.fromUserToUserResponse(user);
    }

    @Transactional
    @Override
    public UserResponse update(UserRequest userRequest) {
        User user = userRepository.findByUsername(userRequest.getUsername());
        if (user == null) {
            log.error(format("No user with username %s", userRequest.getUsername()));
            throw new IllegalArgumentException(format("No user with username %s", userRequest.getUsername()));
        }
        Role role = roleRepository.findByName(userRequest.getRole());
        if (role == null) {
            log.error(format("No such role %s", userRequest.getRole()));
            throw new IllegalArgumentException(format("No such role %s", userRequest.getRole()));
        }

        user = user.toBuilder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .role(role)
                .build();
        log.info(format("Update user %s", userRequest.getUsername()));
        return userAdapter.fromUserToUserResponse(userRepository.save(user));
    }

    @Transactional
    @Override
    public UserResponse addRoleToUser(String username, String role) {
        Role toUpdate = roleRepository.findByName(role);
        if (toUpdate == null) {
            log.error(format("No such role %s", role));
            throw new IllegalArgumentException(format("No such role %s", role));
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error(format("No user with username %s", username));
            throw new IllegalArgumentException(format("No user with username %s", username));
        }

        user = user.toBuilder().role(toUpdate).build();
        log.info(format("User %s updated with role %s", username, role));
        return userAdapter.fromUserToUserResponse(userRepository.save(user));
    }

    @Transactional
    @Override
    public boolean delete(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.warn(format("No such user with username %s", username));
            return false;
        }
        userRepository.delete(user);
        log.info(format("Delete user %s", username));
        return true;
    }
}
