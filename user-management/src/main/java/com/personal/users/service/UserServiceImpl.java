package com.personal.users.service;

import com.personal.users.model.User;
import com.personal.users.model.UserRq;
import com.personal.users.repository.UserRepository;
import com.personal.users.service.contract.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAllUsersByRole(String role) {
        List<User> users = userRepository.findAllUsersByRole(role);
        log.info(format("Retrieve all users for role %s", role));
        return users;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        log.info(format("Retrieve %d users", pageable.getPageSize()));
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
            throw new ResourceAccessException(format("No user with username %s", userRq.getUsername()));
        }
        //TODO: validate that there is such a team and Role
        user.toBuilder()
                .firstName(userRq.getFirstName())
                .lastName(userRq.getLastName())
//                .role()
                .team(userRq.getTeam()).build();
        log.info(format("Update user %s", userRq.getUsername()));
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
