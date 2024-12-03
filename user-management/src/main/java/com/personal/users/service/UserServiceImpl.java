package com.personal.users.service;

import com.personal.users.model.User;
import com.personal.users.repository.UserRepository;
import com.personal.users.service.contract.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        log.info("Retrieve all users");
        return users;
    }
}
