package com.personal.user.service;

import com.personal.user.model.Role;
import com.personal.user.model.User;
import com.personal.user.model.UserRq;
import com.personal.user.repository.RoleRepository;
import com.personal.user.repository.UserRepository;
import com.personal.user.service.contract.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<User> findAllUsersByRole(String role) {
        List<User> users = userRepository.findAllUsersByRole(role);
        log.info(format("Retrieve all project for role %s", role));
        return users;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        log.info(format("Retrieve %d project", pageable.getPageSize()));
        return users;
    }

    @Override
    public Page<User> searchUsersByFirstName(String firstName, Pageable pageable) {
        Page<User> users = userRepository.findByFirstNameContaining(firstName, pageable);
        log.info(format("Retrieve all project with first name %s", firstName));
        return users;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        log.info(format("Retrieve user %s", username));
        return user;
    }

    @Transactional
    @Override
    public User update(UserRq userRq) {
        User user = userRepository.findByUsername(userRq.getUsername());
        if (user == null) {
            log.error(format("No user with username %s", userRq.getUsername()));
            throw new IllegalArgumentException(format("No user with username %s", userRq.getUsername()));
        }
        //TODO: validate that there is such a team
        Role role = roleRepository.findByName(userRq.getRole());
        if (role == null) {
            log.error(format("No such role %s", userRq.getRole()));
            throw new IllegalArgumentException(format("No such role %s", userRq.getRole()));
        }

        user.toBuilder()
                .firstName(userRq.getFirstName())
                .lastName(userRq.getLastName())
                .role(role)
//                .team(userRq.getTeam())
                .build();
        log.info(format("Update user %s", userRq.getUsername()));
        return userRepository.save(user);
    }

    @Override
    public User addRoleToUser(String username, String role) {
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

        user.toBuilder().role(toUpdate);
        log.info(format("User %s updated with role %s", username, role));
        return userRepository.save(user);
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
